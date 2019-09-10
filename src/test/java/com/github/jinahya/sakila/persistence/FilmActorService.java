package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * sakila-entities
 * %%
 * Copyright (C) 2019 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import lombok.extern.slf4j.Slf4j;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * A service class for {@link FilmActor}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
class FilmActorService extends EntityService<FilmActor> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    FilmActorService() {
        super(FilmActor.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Finds the entity whose {@link FilmActor#ATTRIBUTE_NAME_FILM film} attribute and {@link
     * FilmActor#ATTRIBUTE_NAME_ACTOR actor} attribute match specified values.
     * <blockquote><pre>{@code
     * SELECT * FROM film_actor WHERE film_id = ? AND actor_id = ?
     * }</pre></blockquote>
     *
     * @param film  a value for {@link FilmActor#ATTRIBUTE_NAME_FILM film} attribute to match.
     * @param actor a value for {@link FilmActor#ATTRIBUTE_NAME_ACTOR actor} attribute to match.
     * @return an optional of found entity; empty if not found.
     */
    @NotNull Optional<FilmActor> find(@NotNull final Film film, @NotNull final Actor actor) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT fa FROM FilmActor AS fa WHERE fa.film = :film AND fa.actor = :actor")
                    .setParameter("film", film)
                    .setParameter("actor", actor);
            try {
                return Optional.of((FilmActor) query.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final TypedQuery<FilmActor> typedQuery = entityManager().createQuery(
                    "SELECT fa FROM FilmActor AS fa WHERE fa.film = :film AND fa.actor = :actor", FilmActor.class)
                    .setParameter("film", film)
                    .setParameter("actor", actor);
            try {
                return Optional.of(typedQuery.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<FilmActor> criteriaQuery = criteriaBuilder.createQuery(FilmActor.class);
            final Root<FilmActor> filmActor = criteriaQuery.from(FilmActor.class);
            criteriaQuery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(filmActor.get(FilmActor.ATTRIBUTE_NAME_FILM), film),
                    criteriaBuilder.equal(filmActor.get(FilmActor.ATTRIBUTE_NAME_ACTOR), actor)
            ));
            final TypedQuery<FilmActor> typedQuery = entityManager().createQuery(criteriaQuery);
            try {
                return Optional.of(typedQuery.getSingleResult());
            } catch (final NoResultException nre) {
                return Optional.empty();
            }
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<FilmActor> criteriaQuery = criteriaBuilder.createQuery(FilmActor.class);
        final Root<FilmActor> filmActor = criteriaQuery.from(FilmActor.class);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(filmActor.get(FilmActor_.film), film),
                criteriaBuilder.equal(filmActor.get(FilmActor_.actor), actor)
        ));
        final TypedQuery<FilmActor> typedQuery = entityManager().createQuery(criteriaQuery);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    /**
     * Counts all films that specified actor played.
     * <blockquote><pre>{@code
     * SELECT COUNT(*) FROM film_actor WHERE actor_id = ?
     * }</pre></blockquote>
     *
     * @param actor the actor whose films are counted.
     * @return the number of films {@code actor} played.
     */
    @PositiveOrZero long countFilms(@NotNull final Actor actor) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.actor = :actor");
            query.setParameter("actor", actor);
            return (long) query.getSingleResult();
        }
        if (current().nextBoolean()) {
            final TypedQuery<Long> typedQuery = entityManager().createQuery(
                    "SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.actor = :actor", Long.class);
            typedQuery.setParameter("actor", actor);
            return typedQuery.getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<FilmActor> from = criteriaQuery.from(FilmActor.class);
            criteriaQuery.select(criteriaBuilder.count(from));
            criteriaQuery.where(criteriaBuilder.equal(from.get(FilmActor.ATTRIBUTE_NAME_ACTOR), actor));
            final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<FilmActor> from = criteriaQuery.from(FilmActor.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        criteriaQuery.where(criteriaBuilder.equal(from.get(FilmActor_.actor), actor));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Lists films that specified actor ever played, sorted by {@link Film#ATTRIBUTE_NAME_RELEASE_YEAR releaseYear}
     * attribute in descending order.
     * <blockquote><pre>{@code
     * SELECT f.*
     * FROM film_actor AS fa
     *     INNER JOIN film AS f on fa.film_id = f.film_id
     * WHERE fa.actor_id = ?
     * ORDER BY f.release_year DESC
     * LIMIT ?, ?
     * }</pre></blockquote>
     *
     * @param actor       the actor whose films are listed.
     * @param firstResult position of the first result, numbered from {@code 0}.
     * @param maxResults  maximum number of results to retrieve.
     * @return a list of films of {@code actor}.
     */
    @NotNull List<Film> listFilms(@NotNull final Actor actor, @PositiveOrZero final Integer firstResult,
                                  @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT fa.film FROM FilmActor AS fa WHERE fa.actor = :actor ORDER BY fa.film.releaseYear DESC");
            query.setParameter("actor", actor);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Film> films = query.getResultList();
            return films;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Film> typed = entityManager().createQuery(
                    "SELECT fa.film FROM FilmActor AS fa WHERE fa.actor = :actor ORDER BY fa.film.releaseYear DESC",
                    Film.class);
            typed.setParameter("actor", actor);
            ofNullable(firstResult).ifPresent(typed::setFirstResult);
            ofNullable(maxResults).ifPresent(typed::setMaxResults);
            return typed.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Film> criteria = builder.createQuery(Film.class);
            final Root<FilmActor> from = criteria.from(FilmActor.class);
            criteria.select(from.get(FilmActor.ATTRIBUTE_NAME_FILM));
            criteria.where(builder.equal(from.get(FilmActor.ATTRIBUTE_NAME_ACTOR), actor));
            criteria.orderBy(builder.desc(
                    from.get(FilmActor.ATTRIBUTE_NAME_FILM).get(Film.ATTRIBUTE_NAME_RELEASE_YEAR)));
            final TypedQuery<Film> typed = entityManager().createQuery(criteria);
            ofNullable(firstResult).ifPresent(typed::setFirstResult);
            ofNullable(maxResults).ifPresent(typed::setMaxResults);
            return typed.getResultList();
        }
        final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Film> criteria = builder.createQuery(Film.class);
        final Root<FilmActor> from = criteria.from(FilmActor.class);
        criteria.select(from.get(FilmActor_.film));
        criteria.where(builder.equal(from.get(FilmActor_.actor), actor));
        criteria.orderBy(builder.desc(from.get(FilmActor_.film).get(Film_.releaseYear)));
        final TypedQuery<Film> typed = entityManager().createQuery(criteria);
        ofNullable(firstResult).ifPresent(typed::setFirstResult);
        ofNullable(maxResults).ifPresent(typed::setMaxResults);
        return typed.getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Count all actors played in specified film.
     *
     * @param film the film whose actors are counted.
     * @return the number of actors mapped to specified film.
     */
    @PositiveOrZero long countActors(@NotNull final Film film) {
        if (current().nextBoolean()) {
            return (long) entityManager()
                    .createQuery("SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.film = :film")
                    .setParameter("film", film)
                    .getSingleResult();
        }
        if (current().nextBoolean()) {
            return entityManager()
                    .createQuery("SELECT COUNT(fa) FROM FilmActor AS fa WHERE fa.film = :film", Long.class)
                    .setParameter("film", film)
                    .getSingleResult();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            final Root<FilmActor> filmActor = criteriaQuery.from(FilmActor.class);
            criteriaQuery.select(criteriaBuilder.count(filmActor));
            criteriaQuery.where(criteriaBuilder.equal(filmActor.get(FilmActor.ATTRIBUTE_NAME_FILM), film));
            final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
            return typedQuery.getSingleResult();
        }
        final CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        final Root<FilmActor> filmActor = criteriaQuery.from(FilmActor.class);
        criteriaQuery.select(criteriaBuilder.count(filmActor));
        criteriaQuery.where(criteriaBuilder.equal(filmActor.get(FilmActor_.film), film));
        final TypedQuery<Long> typedQuery = entityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * Lists actors played in specified film ordered by {@link Actor#ATTRIBUTE_NAME_FIRST_NAME firstName} in ascending
     * order.
     * <blockquote><pre>{@code
     * SELECT a.*
     * FROM film_actor AS fa
     *     INNER JOIN actor AS a ON fa.actor_id = a.id
     * WHERE fa.film_id = ?
     * ORDER BY a.first_name ASC
     * LIMIT ?, ?
     * }</pre></blockquote>
     *
     * @param film        the film whose actors are listed.
     * @param firstResult a value for {@link TypedQuery#setFirstResult(int)}.
     * @param maxResults  a value for {@link TypedQuery#setMaxResults(int)}.
     * @return a list of mapped actors of specified film.
     */
    @NotNull List<Actor> listActors(@NotNull final Film film, @PositiveOrZero final Integer firstResult,
                                    @Positive final Integer maxResults) {
        if (current().nextBoolean()) {
            final Query query = entityManager().createQuery(
                    "SELECT fa.actor FROM FilmActor AS fa WHERE fa.film = :film ORDER BY fa.actor.firstName ASC");
            query.setParameter("film", film);
            ofNullable(firstResult).ifPresent(query::setFirstResult);
            ofNullable(maxResults).ifPresent(query::setMaxResults);
            @SuppressWarnings({"unchecked"})
            final List<Actor> list = query.getResultList();
            return list;
        }
        if (current().nextBoolean()) {
            final TypedQuery<Actor> typed = entityManager().createQuery(
                    "SELECT fa.actor FROM FilmActor AS fa WHERE fa.film = :film ORDER BY fa.actor.firstName ASC",
                    Actor.class);
            typed.setParameter("film", film);
            ofNullable(firstResult).ifPresent(typed::setFirstResult);
            ofNullable(maxResults).ifPresent(typed::setMaxResults);
            return typed.getResultList();
        }
        if (current().nextBoolean()) {
            final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
            final CriteriaQuery<Actor> criteria = builder.createQuery(Actor.class);
            final Root<FilmActor> from = criteria.from(FilmActor.class);
            criteria.select(from.get(FilmActor.ATTRIBUTE_NAME_ACTOR));
            criteria.where(builder.equal(from.get(FilmActor.ATTRIBUTE_NAME_FILM), film));
            criteria.orderBy(builder.asc(
                    from.get(FilmActor.ATTRIBUTE_NAME_ACTOR).get(Actor.ATTRIBUTE_NAME_FIRST_NAME)));
            final TypedQuery<Actor> typed = entityManager().createQuery(criteria);
            ofNullable(firstResult).ifPresent(typed::setFirstResult);
            ofNullable(maxResults).ifPresent(typed::setMaxResults);
            return typed.getResultList();
        }
        final CriteriaBuilder builder = entityManager().getCriteriaBuilder();
        final CriteriaQuery<Actor> criteria = builder.createQuery(Actor.class);
        final Root<FilmActor> from = criteria.from(FilmActor.class);
        criteria.select(from.get(FilmActor_.actor));
        criteria.where(builder.equal(from.get(FilmActor_.film), film));
        criteria.orderBy(builder.asc(from.get(FilmActor_.actor).get(Actor_.firstName)));
        final TypedQuery<Actor> typed = entityManager().createQuery(criteria);
        ofNullable(firstResult).ifPresent(typed::setFirstResult);
        ofNullable(maxResults).ifPresent(typed::setMaxResults);
        return typed.getResultList();
    }
}
