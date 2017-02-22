package com.hadippa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 10-11-2016.
 */

public class MeraEventPartyModel implements Serializable {


    /**
     * success : true
     * data : [{"id":115910,"ownerId":367385,"startDate":"2017-03-10 09:00:00","endDate":"2017-03-10 21:00:00","title":"The Healthcare MarTech Excellence Awards 2017","description":"<p class=\"font_8\" style=\"text-align: justify;\"><span class=\"color_15\">The Healthcare MarTech Conclave 2017 will be the premier conference and expo for exploring what is new in eHealth, mHealth, Telehealth &ndash; from the perspective of healthcare delivery, clinical <\/span><span class=\"color_15\">care<\/span><span class=\"color_15\">, and <\/span><span class=\"color_15\">patient/consumer<\/span><span class=\"color_15\"> engagement to new technologies, research, investment activities, policy and shifts in the business environment. Thus a platform to explore the vision through Keynote Presentations, Moderated Panel Discussions, Roundtables, Interactive response sessions, networking and more.<\/span><\/p>\n<p class=\"font_8\" style=\"text-align: justify;\"><span class=\"color_15\">The Healthcare MarTech Conclave 2017 will also be <\/span><span class=\"color_15\">recognizing<\/span><span class=\"color_15\"> exceptional work done in various categories leading to excellence in the Modern Healthcare Marketing using Digitization.<\/span><\/p>\n<p class=\"font_8\" style=\"text-align: justify;\"><span class=\"color_15\">It is a one-of-a-kind opportunity to learn from and network with colleagues from across the country.<\/span><\/p>","localityId":0,"url":"the-healthcare-martech-excellence-awards-2017","thumbnailfileid":0,"bannerfileId":0,"categoryId":2,"subcategoryId":190,"pincode":400055,"registrationType":2,"eventMode":0,"timeZoneId":1,"venueName":"Grand Hyatt Mumbai","private":0,"status":1,"latitude":19.0774,"longitude":72.8514,"acceptmeeffortcommission":1,"bannerPath":"https://static.meraevents.com/content/eventbanner/115910/google-form_banner1483381194.jpg","thumbnailPath":"https://static.meraevents.com/content/eventlogo/115910/Healthcare-MarTech-Conclave-2016-and-Excellence-Awards_thumb1483381195.png","categoryName":"Professional","categoryThemeColor":"#2ebcd1","defaultthumbnailPath":"https://static.meraevents.com/content/categorylogo/Professional1455800570.jpg","subCategoryName":"Seminar / Conference / Workshop / Exhibition","eventUrl":"https://www.meraevents.com/event/the-healthcare-martech-excellence-awards-2017","tags":"mumbai,seminar / conference / workshop / exhibition,professional","bookMarked":0,"contactdetails":"","bookButtonValue":"Register Now","facebookLink":"","googleLink":null,"twitterLink":null,"tnctype":"organizer","meraeventstnc":null,"organizertnc":null,"contactWebsiteUrl":"http://healthcaremartech.com","limitSingleTicketType":0,"salespersonid":58,"contactdisplay":0,"customvalidationfunction":null,"customvalidationflag":"0","countryId":14,"countryName":"India","stateId":20,"stateName":"Maharashtra","cityId":14,"cityName":"Mumbai","address1":"Santacruz East, Vakola","address2":"","timeZone":"","timeZoneName":"Asia/Kolkata","ticket_id":115766,"ticket_name":"HMEA2017 - Campaign Awards Entry","ticket_price":20000,"ticket_quantity":100,"ticket_minOrderQuantity":1,"ticket_maxOrderQuantity":1,"ticket_startDate":"2017-01-02 23:30:00","ticket_endDate":"2017-02-03 23:59:00","ticket_type":"paid","ticket_currencyCode":"INR","ticket_currencySymbol":"","ticket_totalSoldTickets":0,"ticket_soldout":0,"ticket_status":1,"created_at":"2017-01-05 18:33:48","updated_at":"2017-01-05 18:33:48","tickets":{"taxList":{"115766":[8],"115767":[8]},"taxDetails":{"115766":[{"taxmappingid":8,"label":"Service Tax","value":15}],"115767":{"1":{"taxmappingid":8,"label":"Service Tax","value":15}}},"ticketList":[{"id":115766,"name":"HMEA2017 - Campaign Awards Entry","description":"","eventId":115910,"price":15000,"quantity":100,"minOrderQuantity":1,"maxOrderQuantity":1,"startDate":"2017-01-02 23:30:00","endDate":"2017-02-25 23:59:00","status":1,"totalSoldTickets":0,"type":"paid","order":1,"displayStatus":1,"currencyId":1,"soldout":0,"currencyCode":"INR","currencySymbol":"","upcomingTicket":0,"pastTicket":0},{"id":115767,"name":"HMEA2017 - Grand Awards Entry","description":"","eventId":115910,"price":15000,"quantity":100,"minOrderQuantity":1,"maxOrderQuantity":1,"startDate":"2017-01-02 23:47:00","endDate":"2017-02-25 23:59:00","status":1,"totalSoldTickets":0,"type":"paid","order":1,"displayStatus":1,"currencyId":1,"soldout":0,"currencyCode":"INR","currencySymbol":"","upcomingTicket":0,"pastTicket":0}],"total":2,"messages":[]}}]
     */

    private boolean success;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable{
        /**
         * id : 115910
         * ownerId : 367385
         * startDate : 2017-03-10 09:00:00
         * endDate : 2017-03-10 21:00:00
         * title : The Healthcare MarTech Excellence Awards 2017
         * description : <p class="font_8" style="text-align: justify;"><span class="color_15">The Healthcare MarTech Conclave 2017 will be the premier conference and expo for exploring what is new in eHealth, mHealth, Telehealth &ndash; from the perspective of healthcare delivery, clinical </span><span class="color_15">care</span><span class="color_15">, and </span><span class="color_15">patient/consumer</span><span class="color_15"> engagement to new technologies, research, investment activities, policy and shifts in the business environment. Thus a platform to explore the vision through Keynote Presentations, Moderated Panel Discussions, Roundtables, Interactive response sessions, networking and more.</span></p>
         <p class="font_8" style="text-align: justify;"><span class="color_15">The Healthcare MarTech Conclave 2017 will also be </span><span class="color_15">recognizing</span><span class="color_15"> exceptional work done in various categories leading to excellence in the Modern Healthcare Marketing using Digitization.</span></p>
         <p class="font_8" style="text-align: justify;"><span class="color_15">It is a one-of-a-kind opportunity to learn from and network with colleagues from across the country.</span></p>
         * localityId : 0
         * url : the-healthcare-martech-excellence-awards-2017
         * thumbnailfileid : 0
         * bannerfileId : 0
         * categoryId : 2
         * subcategoryId : 190
         * pincode : 400055
         * registrationType : 2
         * eventMode : 0
         * timeZoneId : 1
         * venueName : Grand Hyatt Mumbai
         * private : 0
         * status : 1
         * latitude : 19.0774
         * longitude : 72.8514
         * acceptmeeffortcommission : 1
         * bannerPath : https://static.meraevents.com/content/eventbanner/115910/google-form_banner1483381194.jpg
         * thumbnailPath : https://static.meraevents.com/content/eventlogo/115910/Healthcare-MarTech-Conclave-2016-and-Excellence-Awards_thumb1483381195.png
         * categoryName : Professional
         * categoryThemeColor : #2ebcd1
         * defaultthumbnailPath : https://static.meraevents.com/content/categorylogo/Professional1455800570.jpg
         * subCategoryName : Seminar / Conference / Workshop / Exhibition
         * eventUrl : https://www.meraevents.com/event/the-healthcare-martech-excellence-awards-2017
         * tags : mumbai,seminar / conference / workshop / exhibition,professional
         * bookMarked : 0
         * contactdetails :
         * bookButtonValue : Register Now
         * facebookLink :
         * googleLink : null
         * twitterLink : null
         * tnctype : organizer
         * meraeventstnc : null
         * organizertnc : null
         * contactWebsiteUrl : http://healthcaremartech.com
         * limitSingleTicketType : 0
         * salespersonid : 58
         * contactdisplay : 0
         * customvalidationfunction : null
         * customvalidationflag : 0
         * countryId : 14
         * countryName : India
         * stateId : 20
         * stateName : Maharashtra
         * cityId : 14
         * cityName : Mumbai
         * address1 : Santacruz East, Vakola
         * address2 :
         * timeZone :
         * timeZoneName : Asia/Kolkata
         * ticket_id : 115766
         * ticket_name : HMEA2017 - Campaign Awards Entry
         * ticket_price : 20000
         * ticket_quantity : 100
         * ticket_minOrderQuantity : 1
         * ticket_maxOrderQuantity : 1
         * ticket_startDate : 2017-01-02 23:30:00
         * ticket_endDate : 2017-02-03 23:59:00
         * ticket_type : paid
         * ticket_currencyCode : INR
         * ticket_currencySymbol :
         * ticket_totalSoldTickets : 0
         * ticket_soldout : 0
         * ticket_status : 1
         * created_at : 2017-01-05 18:33:48
         * updated_at : 2017-01-05 18:33:48
         * tickets : {"taxList":{"115766":[8],"115767":[8]},"taxDetails":{"115766":[{"taxmappingid":8,"label":"Service Tax","value":15}],"115767":{"1":{"taxmappingid":8,"label":"Service Tax","value":15}}},"ticketList":[{"id":115766,"name":"HMEA2017 - Campaign Awards Entry","description":"","eventId":115910,"price":15000,"quantity":100,"minOrderQuantity":1,"maxOrderQuantity":1,"startDate":"2017-01-02 23:30:00","endDate":"2017-02-25 23:59:00","status":1,"totalSoldTickets":0,"type":"paid","order":1,"displayStatus":1,"currencyId":1,"soldout":0,"currencyCode":"INR","currencySymbol":"","upcomingTicket":0,"pastTicket":0},{"id":115767,"name":"HMEA2017 - Grand Awards Entry","description":"","eventId":115910,"price":15000,"quantity":100,"minOrderQuantity":1,"maxOrderQuantity":1,"startDate":"2017-01-02 23:47:00","endDate":"2017-02-25 23:59:00","status":1,"totalSoldTickets":0,"type":"paid","order":1,"displayStatus":1,"currencyId":1,"soldout":0,"currencyCode":"INR","currencySymbol":"","upcomingTicket":0,"pastTicket":0}],"total":2,"messages":[]}
         */

        private int id;
        private int ownerId;
        private String startDate;
        private String endDate;
        private String title;
        private String description;
        private int localityId;
        private String url;
        private int thumbnailfileid;
        private int bannerfileId;
        private int categoryId;
        private int subcategoryId;
        private int pincode;
        private int registrationType;
        private int eventMode;
        private int timeZoneId;
        private String venueName;
        @SerializedName("private")
        private int privateX;
        private int status;
        private double latitude;
        private double longitude;
        private int acceptmeeffortcommission;
        private String bannerPath;
        private String thumbnailPath;
        private String categoryName;
        private String categoryThemeColor;
        private String defaultthumbnailPath;
        private String subCategoryName;
        private String eventUrl;
        private String tags;
        private int bookMarked;
        private String contactdetails;
        private String bookButtonValue;
        private String facebookLink;
        private Object googleLink;
        private Object twitterLink;
        private String tnctype;
        private Object meraeventstnc;
        private Object organizertnc;
        private String contactWebsiteUrl;
        private int limitSingleTicketType;
        private int salespersonid;
        private int contactdisplay;
        private Object customvalidationfunction;
        private String customvalidationflag;
        private int countryId;
        private String countryName;
        private int stateId;
        private String stateName;
        private int cityId;
        private String cityName;
        private String address1;
        private String address2;
        private String timeZone;
        private String timeZoneName;
        private int ticket_id;
        private String ticket_name;
        private int ticket_price;
        private int ticket_quantity;
        private int ticket_minOrderQuantity;
        private int ticket_maxOrderQuantity;
        private String ticket_startDate;
        private String ticket_endDate;
        private String ticket_type;
        private String ticket_currencyCode;
        private String ticket_currencySymbol;
        private int ticket_totalSoldTickets;
        private int ticket_soldout;
        private int ticket_status;
        private String created_at;
        private String updated_at;
        private TicketsBean tickets;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getLocalityId() {
            return localityId;
        }

        public void setLocalityId(int localityId) {
            this.localityId = localityId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getThumbnailfileid() {
            return thumbnailfileid;
        }

        public void setThumbnailfileid(int thumbnailfileid) {
            this.thumbnailfileid = thumbnailfileid;
        }

        public int getBannerfileId() {
            return bannerfileId;
        }

        public void setBannerfileId(int bannerfileId) {
            this.bannerfileId = bannerfileId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getSubcategoryId() {
            return subcategoryId;
        }

        public void setSubcategoryId(int subcategoryId) {
            this.subcategoryId = subcategoryId;
        }

        public int getPincode() {
            return pincode;
        }

        public void setPincode(int pincode) {
            this.pincode = pincode;
        }

        public int getRegistrationType() {
            return registrationType;
        }

        public void setRegistrationType(int registrationType) {
            this.registrationType = registrationType;
        }

        public int getEventMode() {
            return eventMode;
        }

        public void setEventMode(int eventMode) {
            this.eventMode = eventMode;
        }

        public int getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(int timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public String getVenueName() {
            return venueName;
        }

        public void setVenueName(String venueName) {
            this.venueName = venueName;
        }

        public int getPrivateX() {
            return privateX;
        }

        public void setPrivateX(int privateX) {
            this.privateX = privateX;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getAcceptmeeffortcommission() {
            return acceptmeeffortcommission;
        }

        public void setAcceptmeeffortcommission(int acceptmeeffortcommission) {
            this.acceptmeeffortcommission = acceptmeeffortcommission;
        }

        public String getBannerPath() {
            return bannerPath;
        }

        public void setBannerPath(String bannerPath) {
            this.bannerPath = bannerPath;
        }

        public String getThumbnailPath() {
            return thumbnailPath;
        }

        public void setThumbnailPath(String thumbnailPath) {
            this.thumbnailPath = thumbnailPath;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryThemeColor() {
            return categoryThemeColor;
        }

        public void setCategoryThemeColor(String categoryThemeColor) {
            this.categoryThemeColor = categoryThemeColor;
        }

        public String getDefaultthumbnailPath() {
            return defaultthumbnailPath;
        }

        public void setDefaultthumbnailPath(String defaultthumbnailPath) {
            this.defaultthumbnailPath = defaultthumbnailPath;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getEventUrl() {
            return eventUrl;
        }

        public void setEventUrl(String eventUrl) {
            this.eventUrl = eventUrl;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public int getBookMarked() {
            return bookMarked;
        }

        public void setBookMarked(int bookMarked) {
            this.bookMarked = bookMarked;
        }

        public String getContactdetails() {
            return contactdetails;
        }

        public void setContactdetails(String contactdetails) {
            this.contactdetails = contactdetails;
        }

        public String getBookButtonValue() {
            return bookButtonValue;
        }

        public void setBookButtonValue(String bookButtonValue) {
            this.bookButtonValue = bookButtonValue;
        }

        public String getFacebookLink() {
            return facebookLink;
        }

        public void setFacebookLink(String facebookLink) {
            this.facebookLink = facebookLink;
        }

        public Object getGoogleLink() {
            return googleLink;
        }

        public void setGoogleLink(Object googleLink) {
            this.googleLink = googleLink;
        }

        public Object getTwitterLink() {
            return twitterLink;
        }

        public void setTwitterLink(Object twitterLink) {
            this.twitterLink = twitterLink;
        }

        public String getTnctype() {
            return tnctype;
        }

        public void setTnctype(String tnctype) {
            this.tnctype = tnctype;
        }

        public Object getMeraeventstnc() {
            return meraeventstnc;
        }

        public void setMeraeventstnc(Object meraeventstnc) {
            this.meraeventstnc = meraeventstnc;
        }

        public Object getOrganizertnc() {
            return organizertnc;
        }

        public void setOrganizertnc(Object organizertnc) {
            this.organizertnc = organizertnc;
        }

        public String getContactWebsiteUrl() {
            return contactWebsiteUrl;
        }

        public void setContactWebsiteUrl(String contactWebsiteUrl) {
            this.contactWebsiteUrl = contactWebsiteUrl;
        }

        public int getLimitSingleTicketType() {
            return limitSingleTicketType;
        }

        public void setLimitSingleTicketType(int limitSingleTicketType) {
            this.limitSingleTicketType = limitSingleTicketType;
        }

        public int getSalespersonid() {
            return salespersonid;
        }

        public void setSalespersonid(int salespersonid) {
            this.salespersonid = salespersonid;
        }

        public int getContactdisplay() {
            return contactdisplay;
        }

        public void setContactdisplay(int contactdisplay) {
            this.contactdisplay = contactdisplay;
        }

        public Object getCustomvalidationfunction() {
            return customvalidationfunction;
        }

        public void setCustomvalidationfunction(Object customvalidationfunction) {
            this.customvalidationfunction = customvalidationfunction;
        }

        public String getCustomvalidationflag() {
            return customvalidationflag;
        }

        public void setCustomvalidationflag(String customvalidationflag) {
            this.customvalidationflag = customvalidationflag;
        }

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public int getStateId() {
            return stateId;
        }

        public void setStateId(int stateId) {
            this.stateId = stateId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getTimeZoneName() {
            return timeZoneName;
        }

        public void setTimeZoneName(String timeZoneName) {
            this.timeZoneName = timeZoneName;
        }

        public int getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(int ticket_id) {
            this.ticket_id = ticket_id;
        }

        public String getTicket_name() {
            return ticket_name;
        }

        public void setTicket_name(String ticket_name) {
            this.ticket_name = ticket_name;
        }

        public int getTicket_price() {
            return ticket_price;
        }

        public void setTicket_price(int ticket_price) {
            this.ticket_price = ticket_price;
        }

        public int getTicket_quantity() {
            return ticket_quantity;
        }

        public void setTicket_quantity(int ticket_quantity) {
            this.ticket_quantity = ticket_quantity;
        }

        public int getTicket_minOrderQuantity() {
            return ticket_minOrderQuantity;
        }

        public void setTicket_minOrderQuantity(int ticket_minOrderQuantity) {
            this.ticket_minOrderQuantity = ticket_minOrderQuantity;
        }

        public int getTicket_maxOrderQuantity() {
            return ticket_maxOrderQuantity;
        }

        public void setTicket_maxOrderQuantity(int ticket_maxOrderQuantity) {
            this.ticket_maxOrderQuantity = ticket_maxOrderQuantity;
        }

        public String getTicket_startDate() {
            return ticket_startDate;
        }

        public void setTicket_startDate(String ticket_startDate) {
            this.ticket_startDate = ticket_startDate;
        }

        public String getTicket_endDate() {
            return ticket_endDate;
        }

        public void setTicket_endDate(String ticket_endDate) {
            this.ticket_endDate = ticket_endDate;
        }

        public String getTicket_type() {
            return ticket_type;
        }

        public void setTicket_type(String ticket_type) {
            this.ticket_type = ticket_type;
        }

        public String getTicket_currencyCode() {
            return ticket_currencyCode;
        }

        public void setTicket_currencyCode(String ticket_currencyCode) {
            this.ticket_currencyCode = ticket_currencyCode;
        }

        public String getTicket_currencySymbol() {
            return ticket_currencySymbol;
        }

        public void setTicket_currencySymbol(String ticket_currencySymbol) {
            this.ticket_currencySymbol = ticket_currencySymbol;
        }

        public int getTicket_totalSoldTickets() {
            return ticket_totalSoldTickets;
        }

        public void setTicket_totalSoldTickets(int ticket_totalSoldTickets) {
            this.ticket_totalSoldTickets = ticket_totalSoldTickets;
        }

        public int getTicket_soldout() {
            return ticket_soldout;
        }

        public void setTicket_soldout(int ticket_soldout) {
            this.ticket_soldout = ticket_soldout;
        }

        public int getTicket_status() {
            return ticket_status;
        }

        public void setTicket_status(int ticket_status) {
            this.ticket_status = ticket_status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public TicketsBean getTickets() {
            return tickets;
        }

        public void setTickets(TicketsBean tickets) {
            this.tickets = tickets;
        }

        public static class TicketsBean  implements Serializable{
            /**
             * taxList : {"115766":[8],"115767":[8]}
             * taxDetails : {"115766":[{"taxmappingid":8,"label":"Service Tax","value":15}],"115767":{"1":{"taxmappingid":8,"label":"Service Tax","value":15}}}
             * ticketList : [{"id":115766,"name":"HMEA2017 - Campaign Awards Entry","description":"","eventId":115910,"price":15000,"quantity":100,"minOrderQuantity":1,"maxOrderQuantity":1,"startDate":"2017-01-02 23:30:00","endDate":"2017-02-25 23:59:00","status":1,"totalSoldTickets":0,"type":"paid","order":1,"displayStatus":1,"currencyId":1,"soldout":0,"currencyCode":"INR","currencySymbol":"","upcomingTicket":0,"pastTicket":0},{"id":115767,"name":"HMEA2017 - Grand Awards Entry","description":"","eventId":115910,"price":15000,"quantity":100,"minOrderQuantity":1,"maxOrderQuantity":1,"startDate":"2017-01-02 23:47:00","endDate":"2017-02-25 23:59:00","status":1,"totalSoldTickets":0,"type":"paid","order":1,"displayStatus":1,"currencyId":1,"soldout":0,"currencyCode":"INR","currencySymbol":"","upcomingTicket":0,"pastTicket":0}]
             * total : 2
             * messages : []
             */
            private int total;
            private List<TicketListBean> ticketList;
            private List<?> messages;


            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<TicketListBean> getTicketList() {
                return ticketList;
            }

            public void setTicketList(List<TicketListBean> ticketList) {
                this.ticketList = ticketList;
            }

            public List<?> getMessages() {
                return messages;
            }

            public void setMessages(List<?> messages) {
                this.messages = messages;
            }


            public static class TicketListBean  implements Serializable{
                /**
                 * id : 115766
                 * name : HMEA2017 - Campaign Awards Entry
                 * description :
                 * eventId : 115910
                 * price : 15000
                 * quantity : 100
                 * minOrderQuantity : 1
                 * maxOrderQuantity : 1
                 * startDate : 2017-01-02 23:30:00
                 * endDate : 2017-02-25 23:59:00
                 * status : 1
                 * totalSoldTickets : 0
                 * type : paid
                 * order : 1
                 * displayStatus : 1
                 * currencyId : 1
                 * soldout : 0
                 * currencyCode : INR
                 * currencySymbol :
                 * upcomingTicket : 0
                 * pastTicket : 0
                 */

                private int id;
                private String name;
                private String description;
                private int eventId;
                private int price;
                private int quantity;
                private int minOrderQuantity;
                private int maxOrderQuantity;
                private String startDate;
                private String endDate;
                private int status;
                private int totalSoldTickets;
                private String type;
                private int order;
                private int displayStatus;
                private int currencyId;
                private int soldout;
                private String currencyCode;
                private String currencySymbol;
                private int upcomingTicket;
                private int pastTicket;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public int getEventId() {
                    return eventId;
                }

                public void setEventId(int eventId) {
                    this.eventId = eventId;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public int getMinOrderQuantity() {
                    return minOrderQuantity;
                }

                public void setMinOrderQuantity(int minOrderQuantity) {
                    this.minOrderQuantity = minOrderQuantity;
                }

                public int getMaxOrderQuantity() {
                    return maxOrderQuantity;
                }

                public void setMaxOrderQuantity(int maxOrderQuantity) {
                    this.maxOrderQuantity = maxOrderQuantity;
                }

                public String getStartDate() {
                    return startDate;
                }

                public void setStartDate(String startDate) {
                    this.startDate = startDate;
                }

                public String getEndDate() {
                    return endDate;
                }

                public void setEndDate(String endDate) {
                    this.endDate = endDate;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getTotalSoldTickets() {
                    return totalSoldTickets;
                }

                public void setTotalSoldTickets(int totalSoldTickets) {
                    this.totalSoldTickets = totalSoldTickets;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getOrder() {
                    return order;
                }

                public void setOrder(int order) {
                    this.order = order;
                }

                public int getDisplayStatus() {
                    return displayStatus;
                }

                public void setDisplayStatus(int displayStatus) {
                    this.displayStatus = displayStatus;
                }

                public int getCurrencyId() {
                    return currencyId;
                }

                public void setCurrencyId(int currencyId) {
                    this.currencyId = currencyId;
                }

                public int getSoldout() {
                    return soldout;
                }

                public void setSoldout(int soldout) {
                    this.soldout = soldout;
                }

                public String getCurrencyCode() {
                    return currencyCode;
                }

                public void setCurrencyCode(String currencyCode) {
                    this.currencyCode = currencyCode;
                }

                public String getCurrencySymbol() {
                    return currencySymbol;
                }

                public void setCurrencySymbol(String currencySymbol) {
                    this.currencySymbol = currencySymbol;
                }

                public int getUpcomingTicket() {
                    return upcomingTicket;
                }

                public void setUpcomingTicket(int upcomingTicket) {
                    this.upcomingTicket = upcomingTicket;
                }

                public int getPastTicket() {
                    return pastTicket;
                }

                public void setPastTicket(int pastTicket) {
                    this.pastTicket = pastTicket;
                }
            }
        }
    }
}
