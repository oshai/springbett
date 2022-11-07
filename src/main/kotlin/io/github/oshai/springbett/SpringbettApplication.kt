package io.github.oshai.springbett

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

@SpringBootApplication
class SpringbettApplication {

    @Bean
    fun frontend() = coRouter {
        GET("/api/account/externalLogins") {
            ServerResponse.ok().bodyValueAndAwait(listOf<Any>())
        }
        GET("/api/games") {
            ServerResponse.ok().bodyValueAndAwait("""[
  {
    "GameId": 1,
    "RatioWeight": 1.00,
    "HomeRatio": 2.30,
    "TieRatio": 2.80,
    "AwayRatio": 3.00,
    "HomeTeam": {
      "TeamId": 24,
      "Name": "QATAR",
      "Flag": "https://api.fifa.com/api/v1/picture/flags-sq-2/QAT",
      "Logo": "https://api.fifa.com/api/v1/picture/flags-sq-2/QAT",
      "ShortName": "QAT"
    },
    "AwayTeam": {
      "TeamId": 11,
      "Name": "ECUADOR",
      "Flag": "https://api.fifa.com/api/v1/picture/flags-sq-2/ECU",
      "Logo": "https://api.fifa.com/api/v1/picture/flags-sq-2/ECU",
      "ShortName": "ECU"
    },
    "Date": "2022-11-20T15:00:00Z",
    "HomeScore": null,
    "AwayScore": null,
    "CornersMark": null,
    "CardsMark": null,
    "Stadium": {
      "StadiumId": 1,
      "Name": "Al Bayt Stadium",
      "City": "Al Khor",
      "Capacity": 60000
    },
    "UserHasBet": false,
    "CloseTime": "2022-11-20T14:55:00Z",
    "IsOpen": true,
    "IsPendingUpdate": false,
    "IsBetResolved": false,
    "Mark": "Not Played"
  },
  
  {
    "GameId": 48,
    "RatioWeight": 0.00,
    "HomeRatio": 0.00,
    "TieRatio": 0.00,
    "AwayRatio": 0.00,
    "HomeTeam": {
      "TeamId": 5,
      "Name": "CAMERON",
      "Flag": "https://api.fifa.com/api/v1/picture/flags-sq-2/CMR",
      "Logo": "https://api.fifa.com/api/v1/picture/flags-sq-2/CMR",
      "ShortName": "CMR"
    },
    "AwayTeam": {
      "TeamId": 4,
      "Name": "BRAZIL",
      "Flag": "https://api.fifa.com/api/v1/picture/flags-sq-2/BRA",
      "Logo": "https://api.fifa.com/api/v1/picture/flags-sq-2/BRA",
      "ShortName": "BRA"
    },
    "Date": "2022-12-02T18:00:00Z",
    "HomeScore": null,
    "AwayScore": null,
    "CornersMark": null,
    "CardsMark": null,
    "Stadium": {
      "StadiumId": 2,
      "Name": "Lusail Stadium",
      "City": "Lusail",
      "Capacity": 80000
    },
    "UserHasBet": false,
    "CloseTime": "2022-12-02T17:55:00Z",
    "IsOpen": true,
    "IsPendingUpdate": false,
    "IsBetResolved": false,
    "Mark": "Not Played"
  }
]""")
        }
        GET("/api/generalbets/cansubmitbets/") {
            ServerResponse.ok().bodyValueAndAwait(true)
        }
        GET("/api/generalbets/has-bet/oshai") {
            ServerResponse.ok().bodyValueAndAwait(false)
        }
        GET("/api/users/table") {
            ServerResponse.ok().bodyValueAndAwait("""[
  
  {
    "Id": "4991c66b-f05f-49c8-8dd7-8b2da8f3e774",
    "Place": "2",
    "PlaceDiff": "0",
    "Email": "monkey@zoo.com",
    "IsAdmin": false,
    "Username": "Monkey",
    "Name": "Monkey Monk",
    "Points": 0.0,
    "YesterdayPoints": 0.0,
    "Results": 0,
    "Marks": 0,
    "TotalMarks": 0,
    "Corners": 0,
    "YellowCards": 0
  },
   {
    "Id": "4991c66b-f05f-49c8-8dd7-8b2da8f3e774",
    "Place": "2",
    "PlaceDiff": "0",
    "Email": "monkey@zoo.com",
    "IsAdmin": false,
    "Username": "Monkey",
    "Name": "Monkey Monk",
    "Points": 0.0,
    "YesterdayPoints": 0.0,
    "Results": 0,
    "Marks": 0,
    "TotalMarks": 0,
    "Corners": 0,
    "YellowCards": 0
  }
  
]""")
        }
        GET("/api/account/userInfo") {
            ServerResponse.ok().bodyValueAndAwait("""{
  "userName": "oshai",
  "firstName": "Ohad",
  "lastName": "Shai",
  "email": "o@gmail.com",
  "roles": "Admin",
  "hasRegistered": true,
  "loginProvider": null
}""")
        }
        POST("/token") {
            ServerResponse.ok().bodyValueAndAwait("""{"access_token":"","token_type":"bearer","expires_in":3455999,"userName":"oshai","firstName":"Ohad","lastName":"Shai","email":"o@gmail.com@gmail.com","roles":"Admin",".issued":"Mon, 07 Nov 2022 06:13:39 GMT",".expires":"Sat, 17 Dec 2022 06:13:39 GMT"}""")
        }
    }

    @Bean
    fun stadiums(sr: StadiumRepository) = coRouter {
        GET("/api/stadiums") {
            ServerResponse.ok().bodyAndAwait(sr.findAll())
        }
        GET("/api/stadiums/{id}") {
            ServerResponse.ok().bodyValueAndAwait(
                sr.findById(it.pathVariable("id").toInt()) ?: throw Exception(
                    "could not find stadium ${
                        it.pathVariable(
                            "id"
                        )
                    }"
                )
            )
        }
        POST("/api/stadiums/create") {
            ServerResponse.ok().bodyValueAndAwait(sr.save(it.awaitBody()))
        }
        PUT("/api/stadiums/{id}") {
            ServerResponse.ok()
                .bodyValueAndAwait(sr.save(it.awaitBody<Stadium>().copy(id = it.pathVariable("id").toInt())))
        }
        DELETE("/api/stadiums/{id}") {
            ServerResponse.ok().bodyValueAndAwait(sr.deleteById(it.pathVariable("id").toInt()))
        }
    }
    @Bean
    fun teams(tr: TeamRepository) = coRouter {
        GET("/api/teams") {
            ServerResponse.ok().bodyAndAwait(tr.findAll())
        }
        GET("/teams/{id}") {
            ServerResponse.ok().bodyValueAndAwait(
                tr.findById(it.pathVariable("id").toInt()) ?: throw Exception(
                    "could not find team ${
                        it.pathVariable(
                            "id"
                        )
                    }"
                )
            )
        }
        POST("/api/teams/create") {
            ServerResponse.ok().bodyValueAndAwait(tr.save(it.awaitBody()))
        }
        PUT("/api/teams/{id}") {
            ServerResponse.ok()
                .bodyValueAndAwait(tr.save(it.awaitBody<Team>().copy(id = it.pathVariable("id").toInt())))
        }
        DELETE("/api/teams/{id}") {
            ServerResponse.ok().bodyValueAndAwait(tr.deleteById(it.pathVariable("id").toInt()))
        }
    }
    @Bean
    fun players(pr: PlayerRepository) = coRouter {
        GET("/api/players") {
            ServerResponse.ok().bodyAndAwait(pr.findAll())
        }
        GET("/api/players/{id}") {
            ServerResponse.ok().bodyValueAndAwait(
                pr.findById(it.pathVariable("id").toInt()) ?: throw Exception(
                    "could not find player ${
                        it.pathVariable(
                            "id"
                        )
                    }"
                )
            )
        }
        POST("/api/players/create") {
            ServerResponse.ok().bodyValueAndAwait(pr.save(it.awaitBody()))
        }
        PUT("/api/players/{id}") {
            ServerResponse.ok()
                .bodyValueAndAwait(pr.save(it.awaitBody<Player>().copy(id = it.pathVariable("id").toInt())))
        }
        DELETE("/api/players/{id}") {
            ServerResponse.ok().bodyValueAndAwait(pr.deleteById(it.pathVariable("id").toInt()))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringbettApplication>(*args)
}

