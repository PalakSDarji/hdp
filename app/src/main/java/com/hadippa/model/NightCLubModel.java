package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 27-10-2016.
 */

public class NightCLubModel {


    /**
     * success : true
     * response : {"results_found":1,"results_start":0,"results_shown":1,"restaurants":[{"restaurant":{"R":{"res_id":36956},"apikey":"46e6ea43117f6c2850e7c65e99ce128c","id":"36956","name":"Wink - Vivanta By Taj President","url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","location":{"address":"Vivanta by Taj President, 90, Cuffe Parade, Mumbai","locality":"Cuffe Parade","city":"Mumbai","city_id":3,"latitude":"18.9144164590","longitude":"72.8208847716","zipcode":"","country_id":1},"cuisines":"Finger Food, Japanese","average_cost_for_two":4500,"price_range":4,"currency":"Rs.","offers":[],"thumb":"https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2_featured_v2.jpeg","user_rating":{"aggregate_rating":"4.1","rating_text":"Very Good","rating_color":"5BA829","votes":"159"},"photos_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/photos#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","menu_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/menu#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","featured_image":"https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2.jpeg","has_online_delivery":0,"is_delivering_now":0,"deeplink":"zomato://r/36956","has_table_booking":0,"events_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/events#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","establishment_types":[]}}]}
     */

    private boolean success;
    /**
     * results_found : 1
     * results_start : 0
     * results_shown : 1
     * restaurants : [{"restaurant":{"R":{"res_id":36956},"apikey":"46e6ea43117f6c2850e7c65e99ce128c","id":"36956","name":"Wink - Vivanta By Taj President","url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","location":{"address":"Vivanta by Taj President, 90, Cuffe Parade, Mumbai","locality":"Cuffe Parade","city":"Mumbai","city_id":3,"latitude":"18.9144164590","longitude":"72.8208847716","zipcode":"","country_id":1},"cuisines":"Finger Food, Japanese","average_cost_for_two":4500,"price_range":4,"currency":"Rs.","offers":[],"thumb":"https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2_featured_v2.jpeg","user_rating":{"aggregate_rating":"4.1","rating_text":"Very Good","rating_color":"5BA829","votes":"159"},"photos_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/photos#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","menu_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/menu#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","featured_image":"https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2.jpeg","has_online_delivery":0,"is_delivering_now":0,"deeplink":"zomato://r/36956","has_table_booking":0,"events_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/events#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","establishment_types":[]}}]
     */

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
        private int results_found;
        private int results_start;
        private int results_shown;
        /**
         * restaurant : {"R":{"res_id":36956},"apikey":"46e6ea43117f6c2850e7c65e99ce128c","id":"36956","name":"Wink - Vivanta By Taj President","url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","location":{"address":"Vivanta by Taj President, 90, Cuffe Parade, Mumbai","locality":"Cuffe Parade","city":"Mumbai","city_id":3,"latitude":"18.9144164590","longitude":"72.8208847716","zipcode":"","country_id":1},"cuisines":"Finger Food, Japanese","average_cost_for_two":4500,"price_range":4,"currency":"Rs.","offers":[],"thumb":"https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2_featured_v2.jpeg","user_rating":{"aggregate_rating":"4.1","rating_text":"Very Good","rating_color":"5BA829","votes":"159"},"photos_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/photos#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","menu_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/menu#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","featured_image":"https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2.jpeg","has_online_delivery":0,"is_delivering_now":0,"deeplink":"zomato://r/36956","has_table_booking":0,"events_url":"https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/events#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1","establishment_types":[]}
         */

        private List<RestaurantsBean> restaurants;

        public int getResults_found() {
            return results_found;
        }

        public void setResults_found(int results_found) {
            this.results_found = results_found;
        }

        public int getResults_start() {
            return results_start;
        }

        public void setResults_start(int results_start) {
            this.results_start = results_start;
        }

        public int getResults_shown() {
            return results_shown;
        }

        public void setResults_shown(int results_shown) {
            this.results_shown = results_shown;
        }

        public List<RestaurantsBean> getRestaurants() {
            return restaurants;
        }

        public void setRestaurants(List<RestaurantsBean> restaurants) {
            this.restaurants = restaurants;
        }

        public static class RestaurantsBean {
            /**
             * R : {"res_id":36956}
             * apikey : 46e6ea43117f6c2850e7c65e99ce128c
             * id : 36956
             * name : Wink - Vivanta By Taj President
             * url : https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1
             * location : {"address":"Vivanta by Taj President, 90, Cuffe Parade, Mumbai","locality":"Cuffe Parade","city":"Mumbai","city_id":3,"latitude":"18.9144164590","longitude":"72.8208847716","zipcode":"","country_id":1}
             * cuisines : Finger Food, Japanese
             * average_cost_for_two : 4500
             * price_range : 4
             * currency : Rs.
             * offers : []
             * thumb : https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2_featured_v2.jpeg
             * user_rating : {"aggregate_rating":"4.1","rating_text":"Very Good","rating_color":"5BA829","votes":"159"}
             * photos_url : https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/photos#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1
             * menu_url : https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/menu#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1
             * featured_image : https://b.zmtcdn.com/data/pictures/6/36956/c07e6b39deffb73e38f06c0ddb4336e2.jpeg
             * has_online_delivery : 0
             * is_delivering_now : 0
             * deeplink : zomato://r/36956
             * has_table_booking : 0
             * events_url : https://www.zomato.com/mumbai/wink-vivanta-by-taj-president-cuffe-parade/events#tabtop?utm_source=api_basic_user&utm_medium=api&utm_campaign=v2.1
             * establishment_types : []
             */

            private RestaurantBean restaurant;

            public RestaurantBean getRestaurant() {
                return restaurant;
            }

            public void setRestaurant(RestaurantBean restaurant) {
                this.restaurant = restaurant;
            }

            public static class RestaurantBean {
                /**
                 * res_id : 36956
                 */

                private RBean R;
                private String apikey;
                private String id;
                private String name;
                private String url;
                /**
                 * address : Vivanta by Taj President, 90, Cuffe Parade, Mumbai
                 * locality : Cuffe Parade
                 * city : Mumbai
                 * city_id : 3
                 * latitude : 18.9144164590
                 * longitude : 72.8208847716
                 * zipcode :
                 * country_id : 1
                 */

                private LocationBean location;
                private String cuisines;
                private int average_cost_for_two;
                private int price_range;
                private String currency;
                private String thumb;
                /**
                 * aggregate_rating : 4.1
                 * rating_text : Very Good
                 * rating_color : 5BA829
                 * votes : 159
                 */

                private UserRatingBean user_rating;
                private String photos_url;
                private String menu_url;
                private String featured_image;
                private int has_online_delivery;
                private int is_delivering_now;
                private String deeplink;
                private int has_table_booking;
                private String events_url;
                private List<?> offers;
                private List<?> establishment_types;

                public RBean getR() {
                    return R;
                }

                public void setR(RBean R) {
                    this.R = R;
                }

                public String getApikey() {
                    return apikey;
                }

                public void setApikey(String apikey) {
                    this.apikey = apikey;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public LocationBean getLocation() {
                    return location;
                }

                public void setLocation(LocationBean location) {
                    this.location = location;
                }

                public String getCuisines() {
                    return cuisines;
                }

                public void setCuisines(String cuisines) {
                    this.cuisines = cuisines;
                }

                public int getAverage_cost_for_two() {
                    return average_cost_for_two;
                }

                public void setAverage_cost_for_two(int average_cost_for_two) {
                    this.average_cost_for_two = average_cost_for_two;
                }

                public int getPrice_range() {
                    return price_range;
                }

                public void setPrice_range(int price_range) {
                    this.price_range = price_range;
                }

                public String getCurrency() {
                    return currency;
                }

                public void setCurrency(String currency) {
                    this.currency = currency;
                }

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public UserRatingBean getUser_rating() {
                    return user_rating;
                }

                public void setUser_rating(UserRatingBean user_rating) {
                    this.user_rating = user_rating;
                }

                public String getPhotos_url() {
                    return photos_url;
                }

                public void setPhotos_url(String photos_url) {
                    this.photos_url = photos_url;
                }

                public String getMenu_url() {
                    return menu_url;
                }

                public void setMenu_url(String menu_url) {
                    this.menu_url = menu_url;
                }

                public String getFeatured_image() {
                    return featured_image;
                }

                public void setFeatured_image(String featured_image) {
                    this.featured_image = featured_image;
                }

                public int getHas_online_delivery() {
                    return has_online_delivery;
                }

                public void setHas_online_delivery(int has_online_delivery) {
                    this.has_online_delivery = has_online_delivery;
                }

                public int getIs_delivering_now() {
                    return is_delivering_now;
                }

                public void setIs_delivering_now(int is_delivering_now) {
                    this.is_delivering_now = is_delivering_now;
                }

                public String getDeeplink() {
                    return deeplink;
                }

                public void setDeeplink(String deeplink) {
                    this.deeplink = deeplink;
                }

                public int getHas_table_booking() {
                    return has_table_booking;
                }

                public void setHas_table_booking(int has_table_booking) {
                    this.has_table_booking = has_table_booking;
                }

                public String getEvents_url() {
                    return events_url;
                }

                public void setEvents_url(String events_url) {
                    this.events_url = events_url;
                }

                public List<?> getOffers() {
                    return offers;
                }

                public void setOffers(List<?> offers) {
                    this.offers = offers;
                }

                public List<?> getEstablishment_types() {
                    return establishment_types;
                }

                public void setEstablishment_types(List<?> establishment_types) {
                    this.establishment_types = establishment_types;
                }

                public static class RBean {
                    private int res_id;

                    public int getRes_id() {
                        return res_id;
                    }

                    public void setRes_id(int res_id) {
                        this.res_id = res_id;
                    }
                }

                public static class LocationBean {
                    private String address;
                    private String locality;
                    private String city;
                    private int city_id;
                    private String latitude;
                    private String longitude;
                    private String zipcode;
                    private int country_id;

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                    }

                    public String getLocality() {
                        return locality;
                    }

                    public void setLocality(String locality) {
                        this.locality = locality;
                    }

                    public String getCity() {
                        return city;
                    }

                    public void setCity(String city) {
                        this.city = city;
                    }

                    public int getCity_id() {
                        return city_id;
                    }

                    public void setCity_id(int city_id) {
                        this.city_id = city_id;
                    }

                    public String getLatitude() {
                        return latitude;
                    }

                    public void setLatitude(String latitude) {
                        this.latitude = latitude;
                    }

                    public String getLongitude() {
                        return longitude;
                    }

                    public void setLongitude(String longitude) {
                        this.longitude = longitude;
                    }

                    public String getZipcode() {
                        return zipcode;
                    }

                    public void setZipcode(String zipcode) {
                        this.zipcode = zipcode;
                    }

                    public int getCountry_id() {
                        return country_id;
                    }

                    public void setCountry_id(int country_id) {
                        this.country_id = country_id;
                    }
                }

                public static class UserRatingBean {
                    private String aggregate_rating;
                    private String rating_text;
                    private String rating_color;
                    private String votes;

                    public String getAggregate_rating() {
                        return aggregate_rating;
                    }

                    public void setAggregate_rating(String aggregate_rating) {
                        this.aggregate_rating = aggregate_rating;
                    }

                    public String getRating_text() {
                        return rating_text;
                    }

                    public void setRating_text(String rating_text) {
                        this.rating_text = rating_text;
                    }

                    public String getRating_color() {
                        return rating_color;
                    }

                    public void setRating_color(String rating_color) {
                        this.rating_color = rating_color;
                    }

                    public String getVotes() {
                        return votes;
                    }

                    public void setVotes(String votes) {
                        this.votes = votes;
                    }
                }
            }
        }
    }
}
