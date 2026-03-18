package ru.timofey.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.entity.Genre;

import java.util.List;

@Repository
public class AlbumRepositoryImpl implements AlbumRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Album> findByGenreNameAndReleaseYearBetween(String genreName, Integer startYear, Integer endYear) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);
        Root<Album> album = cq.from(Album.class);
        Join<Album, Genre> genre = album.join("genre");

        cq.select(album)
                .where(cb.and(
                        cb.equal(genre.get("name"), genreName),
                        cb.between(album.get("releaseYear"), startYear, endYear)
                ));

        return entityManager.createQuery(cq).getResultList();
    }
}