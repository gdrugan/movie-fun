package org.superbiz.moviefun;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.albums.Album;
import org.superbiz.moviefun.albums.AlbumFixtures;
import org.superbiz.moviefun.albums.AlbumsBean;
import org.superbiz.moviefun.movies.Movie;
import org.superbiz.moviefun.movies.MovieFixtures;
import org.superbiz.moviefun.movies.MoviesBean;

import java.util.Map;

@Controller
public class HomeController {

    private final MoviesBean moviesBean;
    private final AlbumsBean albumsBean;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;
    private final TransactionTemplate albumsTransactionTemplate;
    private final TransactionTemplate moviesTransactionTemplate;

    public HomeController(MoviesBean moviesBean, AlbumsBean albumsBean, MovieFixtures movieFixtures, AlbumFixtures albumFixtures, PlatformTransactionManager albumsPlatformTransactionManager, PlatformTransactionManager moviesPlatformTransactionManager) {
        this.moviesBean = moviesBean;
        this.albumsBean = albumsBean;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
        this.albumsTransactionTemplate = new TransactionTemplate(albumsPlatformTransactionManager);
        this.moviesTransactionTemplate = new TransactionTemplate(moviesPlatformTransactionManager);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (Movie movie : movieFixtures.load()) {
            addMovie(movie);
        }

        for (Album album : albumFixtures.load()) {
            addAlbum(album);
        }

        model.put("movies", moviesBean.getMovies());
        model.put("albums", albumsBean.getAlbums());

        return "setup";
    }

    public void addAlbum(Album album) {
        albumsTransactionTemplate.execute(new TransactionCallback() {
            // the code in this method executes in a transactional context
            public Object doInTransaction(TransactionStatus status) {
                albumsBean.addAlbum(album);
                return album;
            }
        });
    }

    public void addMovie(Movie movie) {
        moviesTransactionTemplate.execute(new TransactionCallback() {
            // the code in this method executes in a transactional context
            public Object doInTransaction(TransactionStatus status) {
                moviesBean.addMovie(movie);
                return movie;
            }
        });
    }
}
