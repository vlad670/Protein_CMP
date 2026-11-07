package band.effective.education.crossplatform.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ProteinApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun fetchProteins(): Pair<List<Protein>, Int> {
        val response: HttpResponse = client.get("https://rest.uniprot.org/uniprotkb/search") {
            parameter("query", "accession:P01308 OR accession:P68871 OR accession:P00533")
            parameter("format", "json")
            header(HttpHeaders.AcceptEncoding, "identity")
        }
        val total = response.headers["x-total-results"]?.toIntOrNull() ?: 0
        val body: UniProtSearchResponse = response.body()
        return body.results.map { it.toProtein() } to total
    }

    suspend fun fetchProtein(accession: String): Protein {
        val response: HttpResponse = client.get("https://rest.uniprot.org/uniprotkb/$accession") {
            parameter("format", "json")
            header(HttpHeaders.AcceptEncoding, "identity")
        }
        return response.body<UniProtEntryDto>().toProtein()
    }
}