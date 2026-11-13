package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.domain.Protein
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
    private const val BASE_URL = "https://rest.uniprot.org/uniprotkb"
    private const val DEFAULT_QUERY = "accession:P01308 OR accession:P68871 OR accession:P00533"

    private val client = HttpClient(createHttpClientEngine()) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun searchProteins(query: String = ""): Pair<List<Protein>, Int> {
        val actualQuery = query.trim().ifEmpty { DEFAULT_QUERY }
        val response: HttpResponse = client.get("$BASE_URL/search") {
            parameter("query", actualQuery)
            parameter("format", "json")
            parameter("size", 25)
            header(HttpHeaders.AcceptEncoding, "identity")
        }
        val total = response.headers["x-total-results"]?.toIntOrNull() ?: 0
        val body: UniProtSearchResponse = response.body()
        return body.results.map { it.toProtein() } to total
    }

    suspend fun fetchProtein(accession: String): Protein {
        val response: HttpResponse = client.get("$BASE_URL/$accession") {
            parameter("format", "json")
            header(HttpHeaders.AcceptEncoding, "identity")
        }
        return response.body<UniProtEntryDto>().toProtein()
    }
}

