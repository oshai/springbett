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
    private val tournamentService: TournamentService,
) {

    fun create() {
        createTournament()
        createTeams()
        createStadiums()
        createPlayers()
        createGames()
    }

    private fun createTournament() {
        tournamentService.create(
            Tournament(
                tournamentId = tournamentId,
                currentPointsUpdate = 0,
                startTime = ZonedDateTime.of(2022, 11, 20, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(), //20/11/2022
                endTime = ZonedDateTime.of(2022, 12, 18, 23, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(), //18/12/2022
            ).setNew()
        )
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
        teamService.create(createTeam("ARGENTINA", "ARG", 5.5.toBigDecimal()))
        teamService.create(createTeam("AUSTRALIA", "AUS", 200.0.toBigDecimal()))
        teamService.create(createTeam("BELGIUM", "BEL", 12.0.toBigDecimal()))
        teamService.create(createTeam("BRAZIL", "BRA", 4.0.toBigDecimal()))
        teamService.create(createTeam("CAMERON", "CMR", 200.0.toBigDecimal()))
        teamService.create(createTeam("CANADA", "CAN", 100.0.toBigDecimal()))
        teamService.create(createTeam("COSTA RICA", "CRC", 500.0.toBigDecimal()))
        teamService.create(createTeam("CROATIA", "CRO", 35.0.toBigDecimal()))
        teamService.create(createTeam("DENMARK", "DEN", 20.0.toBigDecimal()))
        teamService.create(createTeam("ECUADOR", "ECU", 100.0.toBigDecimal()))
        teamService.create(createTeam("ENGLAND", "ENG", 6.0.toBigDecimal()))
        teamService.create(createTeam("FRANCE", "FRA", 6.0.toBigDecimal()))
        teamService.create(createTeam("GERMANY", "GER", 8.0.toBigDecimal()))
        teamService.create(createTeam("GHANA", "GHA", 200.0.toBigDecimal()))
        teamService.create(createTeam("IRAN", "IRN", 400.0.toBigDecimal()))
        teamService.create(createTeam("JAPAN", "JPN", 200.0.toBigDecimal()))
        teamService.create(createTeam("KOREA REPUBLIC", "KOR", 200.0.toBigDecimal()))
        teamService.create(createTeam("MEXICO", "MEX", 100.0.toBigDecimal()))
        teamService.create(createTeam("MOROCCO", "MAR", 150.0.toBigDecimal()))
        teamService.create(createTeam("NETHERLANDS", "NED", 10.0.toBigDecimal()))
        teamService.create(createTeam("POLAND", "POL", 90.0.toBigDecimal()))
        teamService.create(createTeam("PORTUGAL", "POR", 10.0.toBigDecimal()))
        teamService.create(createTeam("QATAR", "QAT", 200.0.toBigDecimal()))
        teamService.create(createTeam("SAUDI ARABIA", "KSA", 500.0.toBigDecimal()))
        teamService.create(createTeam("SENEGAL", "SEN", 60.0.toBigDecimal()))
        teamService.create(createTeam("SERBIA", "SRB", 60.0.toBigDecimal()))
        teamService.create(createTeam("SPAIN", "ESP", 7.0.toBigDecimal()))
        teamService.create(createTeam("SWITZERLAND", "SUI", 60.0.toBigDecimal()))
        teamService.create(createTeam("TUNISIA", "TUN", 300.0.toBigDecimal()))
        teamService.create(createTeam("URUGUAY", "URU", 35.0.toBigDecimal()))
        teamService.create(createTeam("USA", "USA", 70.0.toBigDecimal()))
        teamService.create(createTeam("WALES", "WAL", 100.0.toBigDecimal()))
    }

    private fun createTeam(name: String, shortName: String, ratio: BigDecimal): Team {
        val url = "https://api.fifa.com/api/v1/picture/flags-sq-2/${shortName.lowercase()}"
        return Team(name, shortName, url, url, ratio)
    }

    private fun createPlayers() {
        playerService.create(Player(name = "Harry Kane", shortName = "kane", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Kylian Mbappe", shortName = "mbappe", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Karim Benzema", shortName = "benzema", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Lionel Messi", shortName = "messi", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Neymar Jr", shortName = "neymar", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Cristiano Ronaldo", shortName = "ronaldo", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Romelu Lukaku", shortName = "lukaku", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Vinicius Junior", shortName = "vinicius", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Memphis Depay", shortName = "memphis", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Lautaro Martinez", shortName = "lautaro", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Diogo Jota", shortName = "jota", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Gabriel Jesus", shortName = "jesus", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Timo Werner", shortName = "werner", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Alvaro Morata", shortName = "morata", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Antoine Griezmann", shortName = "griezmann", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Darwin Nunez", shortName = "munez", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Raheem Sterling", shortName = "sterling", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Robert Lewandowski", shortName = "lewandowski", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Kai Havertz", shortName = "havertz", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Serge Gnabry", shortName = "gnabry", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Julian Albert", shortName = "albert", 0.0.toBigDecimal()))
        playerService.create(Player(name = "Other", shortName = "other", 0.0.toBigDecimal()))
    }

    private fun createGames() {
        gameService.create(
            Game(
                homeTeamId = teamService.getByFullName("QATAR").id(),
                awayTeamId = teamService.getByFullName("ECUADOR").id(),
                date = ZonedDateTime.of(2022, 11, 20, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 21, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 21, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 21, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 22, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 22, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 22, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 22, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 23, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 23, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 23, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 23, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 24, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 24, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 24, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 24, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 25, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 25, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 25, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 25, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 26, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 26, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 26, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 26, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 27, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 27, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 27, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 27, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 28, 12, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 28, 15, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 28, 18, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 28, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 29, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 29, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 29, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 29, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 30, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 30, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 30, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 11, 30, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 1, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 1, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 1, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 1, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 2, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 2, 17, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 2, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
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
                date = ZonedDateTime.of(2022, 12, 2, 21, 0, 0, 0, ZoneId.of("IST", ZoneId.SHORT_IDS))
                    .withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime(),
                stadiumId = stadiumService.getByFullName("Lusail Stadium").id(),
                homeRatio = BigDecimal.ONE,
                awayRatio = BigDecimal.ONE,
                tieRatio = BigDecimal.ONE,
                ratioWeight = BigDecimal.ONE,
            )
        )
    }
}