package com.khoiron14.moviecatalogue.model

/**
 * Created by khoiron14 on 7/3/2019.
 */
class TvshowData {
    private var data = arrayOf(
        arrayOf(
            "One-Punch Man",
            "8.0",
            "2015",
            "Saitama is a hero who only became a hero for fun. After three years of “special” training, though, he’s become so strong that he’s practically invincible. In fact, he’s too strong—even his mightiest opponents are taken out with a single punch, and it turns out that being devastatingly powerful is actually kind of a bore. With his passion for being a hero lost along with his hair, yet still faced with new enemies every day, how much longer can he keep it going?",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/iE3s0lG5QVdEHOEZnoAxjmMtvne.jpg"
        ),
        arrayOf(
            "See No Evil: The Moors Murders",
            "6.6",
            "2006",
            "The dramatisation of one of the most notorious killing sprees in British history.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/b71BaRjp9TwxUZodLGgSRIlkfL8.jpg"
        ),
        arrayOf(
            "The Flash",
            "6.7",
            "2014",
            "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/fki3kBlwJzFp8QohL43g9ReV455.jpg"
        ),
        arrayOf(
            "Arrow",
            "5.8",
            "2012",
            "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mo0FP1GxOFZT4UDde7RFDz5APXF.jpg"
        ),
        arrayOf(
            "Grey's Anatomy",
            "6.2",
            "2005",
            "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/eqgIOObafPJitt8JNh1LuO2fvqu.jpg"
        ),
        arrayOf(
            "Fear the Walking Dead",
            "6.3",
            "2015",
            "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/oYXxZIiI7lVh6IUCCikImKwULHB.jpg"
        ),
        arrayOf(
            "The Simpsons",
            "7.1",
            "1989",
            "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/yTZQkSsxUFJZJe67IenRM0AEklc.jpg"
        ),
        arrayOf(
            "Marvel's Agents of S.H.I.E.L.D",
            "6.8",
            "2013",
            "Agent Phil Coulson of S.H.I.E.L.D. (Strategic Homeland Intervention, Enforcement and Logistics Division) puts together a team of agents to investigate the new, the strange and the unknown around the globe, protecting the ordinary from the extraordinary.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/cXiETfFK1BTLest5fhTLfDLRdL6.jpg"
        ),
        arrayOf(
            "Dragon Ball",
            "7.1",
            "1986",
            "Long ago in the mountains, a fighting master known as Gohan discovered a strange boy whom he named Goku. Gohan raised him and trained Goku in martial arts until he died. The young and very strong boy was on his own, but easily managed. Then one day, Goku met a teenage girl named Bulma, whose search for the dragon balls brought her to Goku's home. Together, they set off to find all seven dragon balls in an adventure that would change Goku's life forever. See how Goku met his life long friends Bulma, Yamcha, Krillin, Master Roshi and more. And see his adventures as a boy, all leading up to Dragonball Z and later Dragonball GT.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/3wx3EAMtqnbSLhGG8NrqXriCUIQ.jpg"
        ),
        arrayOf(
            "Game of Thrones",
            "8.1",
            "2011",
            "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg"
        )
    )

    fun getListData(): ArrayList<Tvshow> {
        val list = ArrayList<Tvshow>()
        for (aData in data) {
            val tvshow = Tvshow()
            tvshow.title = aData[0]
            tvshow.rating = aData[1]
            tvshow.releaseYear = aData[2]
            tvshow.overview = aData[3]
            tvshow.poster = aData[4]
            list.add(tvshow)
        }
        return list
    }
}