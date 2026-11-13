package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.domain.Protein
import band.effective.education.crossplatform.domain.RelatedProtein

val mockProteins = listOf(
    Protein(
        primaryAccession = "P01308",
        entryName = "INS_HUMAN",
        proteinName = "Insulin",
        organismName = "Homo sapiens (Human)",
        geneName = "INS",
        sequence = "MALWMRLLPLLALLALWGPDPAAAFVNQHLCGSHLVEALYLVCGERGFFYTPKTRREAEDLQVGQVELGGGPGAGSLQPLALEGSLQKRGIVEQCCTSICSLYQLENYCN",
        related = listOf(
            RelatedProtein(accession = "P06213", geneName = "INSR"),
            RelatedProtein(accession = "P08069", geneName = "IGF1R"),
            RelatedProtein(accession = "P01344", geneName = "IGF2"),
            RelatedProtein(accession = "P05019", geneName = "IGF1")
        )
    ),
    Protein(
        primaryAccession = "P68871",
        entryName = "HBB_HUMAN",
        proteinName = "Hemoglobin subunit beta",
        organismName = "Homo sapiens (Human)",
        geneName = "HBB",
        sequence = "MVHLTPEEKSAVTALWGKVNVDEVGGEALGRLLVVYPWTQRFFESFGDLSTPDAVMGNPKVKAHGKKVLGAFSDGLAHLDNLKGTFATLSELHCDKLHVDPENFRLLGNVLVCVLAHHFGKEFTPPVQAAYQKVVAGVANALAHKYH",
        related = listOf(
            RelatedProtein(accession = "P69905", geneName = "HBA1"),
            RelatedProtein(accession = "P69891", geneName = "HBG1"),
            RelatedProtein(accession = "P02042", geneName = "HBD")
        )
    ),
    Protein(
        primaryAccession = "P00533",
        entryName = "EGFR_HUMAN",
        proteinName = "Epidermal growth factor receptor",
        organismName = "Homo sapiens (Human)",
        geneName = "EGFR",
        sequence = "MRPSGTAGAALLALLAALCPASRALEEKKVCQGTSNKLTQLGTFEDHFLSLQRMFNNCEVVLGNLEITYVQRNYDLSFLKTIQEVAGYVLIALNTVERIPLENLQIIRGNMYYENSYALAVLSNYDANKTGLKELPMRNLQEILHGAVRFSNNPALCNVESIQWRDIVSSDFLSNMSMDFQNHLGSCQKCDPSCPNGSCWGAGEENCQKLTKIICAQQCSHRCRGKSPSDCCHNQCAAGCTGPRESDCLVCRKFRDEATCKDTCPPLMLYNPTTYQMDVNPE",
        related = listOf(
            RelatedProtein(accession = "P01133", geneName = "EGF"),
            RelatedProtein(accession = "P04626", geneName = "ERBB2"),
            RelatedProtein(accession = "P00519", geneName = "ABL1")
        )
    )
)

