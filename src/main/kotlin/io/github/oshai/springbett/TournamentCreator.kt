package io.github.oshai.springbett

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class TournamentCreator(
    private val teamService: TeamService,
    private val stadiumService: StadiumService,
    private val playerService: PlayerService,
    private val gameService: GameService,
) {

    fun create() {
        createTeams()
        createStadiums()
        createPlayers()
        createGames()
    }

    private fun createStadiums() {
        stadiumService.create(Stadium(name = "Al Bayt Stadium", shortName = "bayt", capacity = 60000, city = "Al Khor"))
        stadiumService.create(Stadium(name = "Lusail Stadium", shortName = "lusa", capacity = 80000, city = "Lusail"))
        stadiumService.create(
            Stadium(
                name = "Al Janoub Stadium",
                shortName = "jano",
                capacity = 40000,
                city = "Al Wakrah"
            )
        )
        stadiumService.create(
            Stadium(
                name = "Ahmad Bin Ali Stadium",
                shortName = "bina",
                capacity = 40000,
                city = "Al Rayyan"
            )
        )
        stadiumService.create(
            Stadium(
                name = "Khalifa International Stadium",
                shortName = "khal",
                capacity = 40000,
                city = "Doha"
            )
        )
        stadiumService.create(
            Stadium(
                name = "Education City Stadium",
                shortName = "educ",
                capacity = 40000,
                city = "Doha"
            )
        )
        stadiumService.create(Stadium(name = "Stadium 974", shortName = "s974", capacity = 40000, city = "Doha"))
        stadiumService.create(Stadium(name = "Al Thumama Stadium", shortName = "thum", capacity = 40000, city = "Doha"))
    }

    private fun createTeams() {
        teamService.create(Team("ARGENTINA", "ARG"))
        teamService.create(Team("AUSTRALIA", "AUS"))
        teamService.create(Team("BELGIUM", "BEL"))
        teamService.create(Team("BRAZIL", "BRA"))
        teamService.create(Team("CAMERON", "CMR"))
        teamService.create(Team("CANADA", "CAN"))
        teamService.create(Team("COSTA RICA", "CRC"))
        teamService.create(Team("CROATIA", "CRO"))
        teamService.create(Team("CZECH REPUBLIC", "CZE"))
        teamService.create(Team("DENMARK", "DEN"))
        teamService.create(Team("ECUADOR", "ECU"))
        teamService.create(Team("ENGLAND", "ENG"))
        teamService.create(Team("FRANCE", "FRA"))
        teamService.create(Team("GERMANY", "GER"))
        teamService.create(Team("GHANA", "GHA"))
        teamService.create(Team("IRAN", "IRN"))
        teamService.create(Team("JAPAN", "JPN"))
        teamService.create(Team("KOREA REPUBLIC", "KOR"))
        teamService.create(Team("MEXICO", "MEX"))
        teamService.create(Team("MOROCCO", "MAR"))
        teamService.create(Team("NETHERLANDS", "NED"))
        teamService.create(Team("POLAND", "POL"))
        teamService.create(Team("PORTUGAL", "POR"))
        teamService.create(Team("QATAR", "QAT"))
        teamService.create(Team("SAUDI ARABIA", "KSA"))
        teamService.create(Team("SENEGAL", "SEN"))
        teamService.create(Team("SERBIA", "SRB"))
        teamService.create(Team("SPAIN", "ESP"))
        teamService.create(Team("SWITZERLAND", "SUI"))
        teamService.create(Team("TUNISIA", "TUN"))
        teamService.create(Team("URUGUAY", "URU"))
        teamService.create(Team("USA", "USA"))
        teamService.create(Team("WALES", "WAL"))
    }

    private fun createPlayers() {
        playerService.create(Player(name = "Harry Kane", shortName = "kane"))
        playerService.create(Player(name = "Kylian Mbappe", shortName = "mbappe"))
        playerService.create(Player(name = "Karim Benzema", shortName = "benzema"))
        playerService.create(Player(name = "Lionel Messi", shortName = "messi"))
        playerService.create(Player(name = "Neymar Jr", shortName = "neymar"))
        playerService.create(Player(name = "Cristiano Ronaldo", shortName = "ronaldo"))
        playerService.create(Player(name = "Romelu Lukaku", shortName = "lukaku"))
        playerService.create(Player(name = "Vinicius Junior", shortName = "vinicius"))
        playerService.create(Player(name = "Memphis Depay", shortName = "memphis"))
        playerService.create(Player(name = "Lautaro Martinez", shortName = "lautaro"))
        playerService.create(Player(name = "Diogo Jota", shortName = "jota"))
        playerService.create(Player(name = "Gabriel Jesus", shortName = "jesus"))
        playerService.create(Player(name = "Timo Werner", shortName = "werner"))
        playerService.create(Player(name = "Alvaro Morata", shortName = "morata"))
        playerService.create(Player(name = "Antoine Griezmann", shortName = "griezmann"))
        playerService.create(Player(name = "Darwin Nunez", shortName = "munez"))
        playerService.create(Player(name = "Raheem Sterling", shortName = "sterling"))
        playerService.create(Player(name = "Robert Lewandowski", shortName = "lewandowski"))
        playerService.create(Player(name = "Kai Havertz", shortName = "havertz"))
        playerService.create(Player(name = "Serge Gnabry", shortName = "gnabry"))
        playerService.create(Player(name = "Other", shortName = "other"))
    }

    private fun createGames() {
        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("QATAR").id(),
                awayTeamId = teamService.getByFullName("ECUADOR").id(),
                startTime = ZonedDateTime.of(2022, 11, 20, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Bayt Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("ENGLAND").id(),
                awayTeamId = teamService.getByFullName("IRAN").id(),
                startTime = ZonedDateTime.of(2022, 11, 21, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Khalifa International Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("SENEGAL").id(),
                awayTeamId = teamService.getByFullName("NETHERLANDS").id(),
                startTime = ZonedDateTime.of(2022, 11, 21, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Thumama Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("USA").id(),
                awayTeamId = teamService.getByFullName("WALES").id(),
                startTime = ZonedDateTime.of(2022, 11, 21, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Ahmad Bin Ali Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("ARGENTINA").id(),
                awayTeamId = teamService.getByFullName("SAUDI ARABIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 22, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Lusail Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("DENMARK").id(),
                awayTeamId = teamService.getByFullName("TUNISIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 22, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Education City Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("MEXICO").id(),
                awayTeamId = teamService.getByFullName("POLAND").id(),
                startTime = ZonedDateTime.of(2022, 11, 22, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Stadium 974").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("FRANCE").id(),
                awayTeamId = teamService.getByFullName("AUSTRALIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 22, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Janoub Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("MOROCCO").id(),
                awayTeamId = teamService.getByFullName("CROATIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 23, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Bayt Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("GERMANY").id(),
                awayTeamId = teamService.getByFullName("JAPAN").id(),
                startTime = ZonedDateTime.of(2022, 11, 23, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Khalifa International Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("SPAIN").id(),
                awayTeamId = teamService.getByFullName("COSTA RICA").id(),
                startTime = ZonedDateTime.of(2022, 11, 23, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Thumama Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("BELGIUM").id(),
                awayTeamId = teamService.getByFullName("CANADA").id(),
                startTime = ZonedDateTime.of(2022, 11, 23, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Ahmad Bin Ali Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("SWITZERLAND").id(),
                awayTeamId = teamService.getByFullName("CAMERON").id(),
                startTime = ZonedDateTime.of(2022, 11, 24, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Janoub Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("URUGUAY").id(),
                awayTeamId = teamService.getByFullName("KOREA REPUBLIC").id(),
                startTime = ZonedDateTime.of(2022, 11, 24, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Education City Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("PORTUGAL").id(),
                awayTeamId = teamService.getByFullName("GHANA").id(),
                startTime = ZonedDateTime.of(2022, 11, 24, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Stadium 974").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("BRAZIL").id(),
                awayTeamId = teamService.getByFullName("SERBIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 24, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Lusail Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("WALES").id(),
                awayTeamId = teamService.getByFullName("IRAN").id(),
                startTime = ZonedDateTime.of(2022, 11, 25, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Ahmad Bin Ali Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("QATAR").id(),
                awayTeamId = teamService.getByFullName("SENEGAL").id(),
                startTime = ZonedDateTime.of(2022, 11, 25, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Thumama Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("NETHERLANDS").id(),
                awayTeamId = teamService.getByFullName("ECUADOR").id(),
                startTime = ZonedDateTime.of(2022, 11, 25, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Khalifa International Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("ENGLAND").id(),
                awayTeamId = teamService.getByFullName("USA").id(),
                startTime = ZonedDateTime.of(2022, 11, 25, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Bayt Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("TUNISIA").id(),
                awayTeamId = teamService.getByFullName("AUSTRALIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 26, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Janoub Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("POLAND").id(),
                awayTeamId = teamService.getByFullName("SAUDI ARABIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 26, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Education City Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("FRANCE").id(),
                awayTeamId = teamService.getByFullName("DENMARK").id(),
                startTime = ZonedDateTime.of(2022, 11, 26, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Stadium 974").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("ARGENTINA").id(),
                awayTeamId = teamService.getByFullName("MEXICO").id(),
                startTime = ZonedDateTime.of(2022, 11, 26, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Lusail Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("JAPAN").id(),
                awayTeamId = teamService.getByFullName("COSTA RICA").id(),
                startTime = ZonedDateTime.of(2022, 11, 27, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Ahmad Bin Ali Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("BELGIUM").id(),
                awayTeamId = teamService.getByFullName("MOROCCO").id(),
                startTime = ZonedDateTime.of(2022, 11, 27, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Thumama Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("CROATIA").id(),
                awayTeamId = teamService.getByFullName("CANADA").id(),
                startTime = ZonedDateTime.of(2022, 11, 27, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Khalifa International Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("SPAIN").id(),
                awayTeamId = teamService.getByFullName("GERMANY").id(),
                startTime = ZonedDateTime.of(2022, 11, 27, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Bayt Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("CAMERON").id(),
                awayTeamId = teamService.getByFullName("SERBIA").id(),
                startTime = ZonedDateTime.of(2022, 11, 28, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Janoub Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("KOREA REPUBLIC").id(),
                awayTeamId = teamService.getByFullName("GHANA").id(),
                startTime = ZonedDateTime.of(2022, 11, 28, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Education City Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("BRAZIL").id(),
                awayTeamId = teamService.getByFullName("SWITZERLAND").id(),
                startTime = ZonedDateTime.of(2022, 11, 28, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Stadium 974").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("PORTUGAL").id(),
                awayTeamId = teamService.getByFullName("URUGUAY").id(),
                startTime = ZonedDateTime.of(2022, 11, 28, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Lusail Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("ECUADOR").id(),
                awayTeamId = teamService.getByFullName("SENEGAL").id(),
                startTime = ZonedDateTime.of(2022, 11, 29, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Khalifa International Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("NETHERLANDS").id(),
                awayTeamId = teamService.getByFullName("QATAR").id(),
                startTime = ZonedDateTime.of(2022, 11, 29, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Bayt Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("IRAN").id(),
                awayTeamId = teamService.getByFullName("USA").id(),
                startTime = ZonedDateTime.of(2022, 11, 29, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Thumama Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("WALES").id(),
                awayTeamId = teamService.getByFullName("ENGLAND").id(),
                startTime = ZonedDateTime.of(2022, 11, 29, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Ahmad Bin Ali Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("TUNISIA").id(),
                awayTeamId = teamService.getByFullName("FRANCE").id(),
                startTime = ZonedDateTime.of(2022, 11, 30, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Education City Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("AUSTRALIA").id(),
                awayTeamId = teamService.getByFullName("DENMARK").id(),
                startTime = ZonedDateTime.of(2022, 11, 30, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Janoub Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("POLAND").id(),
                awayTeamId = teamService.getByFullName("ARGENTINA").id(),
                startTime = ZonedDateTime.of(2022, 11, 30, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Stadium 974").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("SAUDI ARABIA").id(),
                awayTeamId = teamService.getByFullName("MEXICO").id(),
                startTime = ZonedDateTime.of(2022, 11, 30, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Lusail Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("CROATIA").id(),
                awayTeamId = teamService.getByFullName("BELGIUM").id(),
                startTime = ZonedDateTime.of(2022, 12, 1, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Ahmad Bin Ali Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("CANADA").id(),
                awayTeamId = teamService.getByFullName("MOROCCO").id(),
                startTime = ZonedDateTime.of(2022, 12, 1, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Thumama Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("JAPAN").id(),
                awayTeamId = teamService.getByFullName("SPAIN").id(),
                startTime = ZonedDateTime.of(2022, 12, 1, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Khalifa International Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("COSTA RICA").id(),
                awayTeamId = teamService.getByFullName("GERMANY").id(),
                startTime = ZonedDateTime.of(2022, 12, 1, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Bayt Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("KOREA REPUBLIC").id(),
                awayTeamId = teamService.getByFullName("PORTUGAL").id(),
                startTime = ZonedDateTime.of(2022, 12, 2, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Education City Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("GHANA").id(),
                awayTeamId = teamService.getByFullName("URUGUAY").id(),
                startTime = ZonedDateTime.of(2022, 12, 2, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Al Janoub Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("SERBIA").id(),
                awayTeamId = teamService.getByFullName("SWITZERLAND").id(),
                startTime = ZonedDateTime.of(2022, 12, 2, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Stadium 974").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )

        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("CAMERON").id(),
                awayTeamId = teamService.getByFullName("BRAZIL").id(),
                startTime = ZonedDateTime.of(2022, 12, 2, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS)).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Lusail Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )
    }
}