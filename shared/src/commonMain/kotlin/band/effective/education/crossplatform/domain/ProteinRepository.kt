package band.effective.education.crossplatform.domain

interface ProteinRepository {
    suspend fun getProteins(query: String = ""): Result<Pair<List<Protein>, Int>>
    suspend fun getProtein(accession: String): Result<Protein>
}

