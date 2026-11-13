package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.domain.Protein
import band.effective.education.crossplatform.domain.ProteinRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProteinRepositoryImpl(
    private val api: ProteinApi = ProteinApi
) : ProteinRepository {

    override suspend fun getProteins(query: String): Result<Pair<List<Protein>, Int>> =
        withContext(Dispatchers.Default) {
            try {
                Result.success(api.searchProteins(query))
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                Result.failure(t)
            }
        }

    override suspend fun getProtein(accession: String): Result<Protein> =
        withContext(Dispatchers.Default) {
            try {
                Result.success(api.fetchProtein(accession))
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                Result.failure(t)
            }
        }
}

