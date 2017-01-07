package com.hadippa.model;

import java.util.List;

/**
 * Created by hp on 1/7/2017.
 */

public class CinemaModel {


    /**
     * success : true
     * response : {"meta_info":{"range_from":0,"range_to":1,"total_count":2336},"movies":[{"id":"12157","slug":"fantastic-beasts-and-where-to-find-them","title":"Fantastic Beasts and Where to Find Them","poster_image_thumbnail":"http://image.tmdb.org/t/p/w154/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg"},{"id":"20338","slug":"rogue-one-a-star-wars-story","title":"Rogue One: A Star Wars Story","poster_image_thumbnail":"http://image.tmdb.org/t/p/w154/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg"}]}
     */

    private boolean success;
    private ResponseBean response;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * meta_info : {"range_from":0,"range_to":1,"total_count":2336}
         * movies : [{"id":"12157","slug":"fantastic-beasts-and-where-to-find-them","title":"Fantastic Beasts and Where to Find Them","poster_image_thumbnail":"http://image.tmdb.org/t/p/w154/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg"},{"id":"20338","slug":"rogue-one-a-star-wars-story","title":"Rogue One: A Star Wars Story","poster_image_thumbnail":"http://image.tmdb.org/t/p/w154/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg"}]
         */

        private MetaInfoBean meta_info;
        private List<MoviesBean> movies;

        public MetaInfoBean getMeta_info() {
            return meta_info;
        }

        public void setMeta_info(MetaInfoBean meta_info) {
            this.meta_info = meta_info;
        }

        public List<MoviesBean> getMovies() {
            return movies;
        }

        public void setMovies(List<MoviesBean> movies) {
            this.movies = movies;
        }

        public static class MetaInfoBean {
            /**
             * range_from : 0
             * range_to : 1
             * total_count : 2336
             */

            private int range_from;
            private int range_to;
            private int total_count;

            public int getRange_from() {
                return range_from;
            }

            public void setRange_from(int range_from) {
                this.range_from = range_from;
            }

            public int getRange_to() {
                return range_to;
            }

            public void setRange_to(int range_to) {
                this.range_to = range_to;
            }

            public int getTotal_count() {
                return total_count;
            }

            public void setTotal_count(int total_count) {
                this.total_count = total_count;
            }
        }

        public static class MoviesBean {
            /**
             * id : 12157
             * slug : fantastic-beasts-and-where-to-find-them
             * title : Fantastic Beasts and Where to Find Them
             * poster_image_thumbnail : http://image.tmdb.org/t/p/w154/gri0DDxsERr6B2sOR1fGLxLpSLx.jpg
             */

            private String id;
            private String slug;
            private String title;
            private String poster_image_thumbnail;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPoster_image_thumbnail() {
                return poster_image_thumbnail;
            }

            public void setPoster_image_thumbnail(String poster_image_thumbnail) {
                this.poster_image_thumbnail = poster_image_thumbnail;
            }
        }
    }
}
