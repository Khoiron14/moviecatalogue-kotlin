package com.khoiron14.moviecatalogue.model

/**
 * Created by khoiron14 on 7/3/2019.
 */
class MovieData {
    private var data = arrayOf(
        arrayOf(
            "Avengers: Infinity War",
            "8.3",
            "2018",
            "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/ypX47SBSThTbB40AIJ22eOUCpjU.jpg"
        ),
        arrayOf(
            "Aquaman",
            "6.8",
            "2018",
            "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/ydUpl3QkVUCHCq1VWvo2rW4Sf7y.jpg"
        ),
        arrayOf(
            "Bird Box",
            "7.0",
            "2018",
            "Five years after an ominous unseen presence drives most of society to suicide, a survivor and her two children make a desperate bid to reach safety.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rGfGfgL2pEPCfhIvqHXieXFn7gp.jpg"
        ),
        arrayOf(
            "Bumblebee",
            "6.5",
            "2018",
            "On the run in the year 1987, Bumblebee finds refuge in a junkyard in a small Californian beach town. Charlie, on the cusp of turning 18 and trying to find her place in the world, discovers Bumblebee, battle-scarred and broken. When Charlie revives him, she quickly learns this is no ordinary yellow VW bug.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/fw02ONlDhrYjTSZV8XO6hhU3ds3.jpg"
        ),
        arrayOf(
            "Creed II",
            "6.7",
            "2018",
            "Between personal obligations and training for his next big fight against an opponent with ties to his family's past, Adonis Creed is up against the challenge of his life.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/qPQFWrLoQYyGxmeBgmpX901Q0mF.jpg"
        ),
        arrayOf(
            "How to Train Your Dragon: The Hidden World",
            "7.6",
            "2019",
            "As Hiccup fulfills his dream of creating a peaceful dragon utopia, Toothless’ discovery of an untamed, elusive mate draws the Night Fury away. When danger mounts at home and Hiccup’s reign as village chief is tested, both dragon and rider must make impossible decisions to save their kind.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/gHoZaNRTCYNjftaqZFjjV15OSHr.jpg"
        ),
        arrayOf(
            "Mortal Engines",
            "6.0",
            "2018",
            "Many thousands of years in the future, Earth’s cities roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive traction cities, the old London, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the wastelands who will change the course of his life forever.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/iteUvQKCW0EqNQrIVzZGJntYq9s.jpg"
        ),
        arrayOf(
            "Robin Hood",
            "5.8",
            "2018",
            "A war-hardened Crusader and his Moorish commander mount an audacious revolt against the corrupt English crown.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/AiRfixFcfTkNbn2A73qVJPlpkUo.jpg"
        ),
        arrayOf(
            "Toy Story 4",
            "7.8",
            "2019",
            "Woody has always been confident about his place in the world and that his priority is taking care of his kid, whether that's Andy or Bonnie. But when Bonnie adds a reluctant new toy called \"Forky\" to her room, a road trip adventure alongside old and new friends will show Woody how big the world can be for a toy.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/p8LjQCBbs4mmjba0i2qins5pZ9p.jpg"
        ),
        arrayOf(
            "Shazam!",
            "7.1",
            "2019",
            "A boy is given the ability to become an adult superhero in times of need with a single magic word.",
            "https://image.tmdb.org/t/p/w600_and_h900_bestv2/xoJ2TExRHwrEdcuLglbQskgW5Q9.jpg"
        )
    )

    fun getListData(): ArrayList<Movie> {
        val list = ArrayList<Movie>()
        for (aData in data) {
            val movie = Movie()
            movie.title = aData[0]
            movie.rating = aData[1]
            movie.releaseYear = aData[2]
            movie.overview = aData[3]
            movie.poster = aData[4]
            list.add(movie)
        }
        return list
    }
}