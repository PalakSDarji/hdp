package com.hadippa.model;

import java.util.List;

/**
 * Created by hp on 2/2/2017.
 */

public class Blocked_Data {


    /**
     * success : true
     * blocked_list : [{"id":2,"blocker_id":226,"blocked_id":210,"created_at":"2017-02-02 00:51:17","updated_at":"2017-02-02 00:51:17","blocked":{"id":210,"first_name":"raj","last_name":"yadav","profile_photo":"default.jpg"}}]
     * next : null
     */

    private boolean success;
    private Object next;
    private List<BlockedListBean> blocked_list;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public List<BlockedListBean> getBlocked_list() {
        return blocked_list;
    }

    public void setBlocked_list(List<BlockedListBean> blocked_list) {
        this.blocked_list = blocked_list;
    }

    public static class BlockedListBean {
        /**
         * id : 2
         * blocker_id : 226
         * blocked_id : 210
         * created_at : 2017-02-02 00:51:17
         * updated_at : 2017-02-02 00:51:17
         * blocked : {"id":210,"first_name":"raj","last_name":"yadav","profile_photo":"default.jpg"}
         */

        private int id;
        private int blocker_id;
        private int blocked_id;
        private String created_at;
        private String updated_at;
        private BlockedBean blocked;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBlocker_id() {
            return blocker_id;
        }

        public void setBlocker_id(int blocker_id) {
            this.blocker_id = blocker_id;
        }

        public int getBlocked_id() {
            return blocked_id;
        }

        public void setBlocked_id(int blocked_id) {
            this.blocked_id = blocked_id;
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

        public BlockedBean getBlocked() {
            return blocked;
        }

        public void setBlocked(BlockedBean blocked) {
            this.blocked = blocked;
        }

        public static class BlockedBean {
            /**
             * id : 210
             * first_name : raj
             * last_name : yadav
             * profile_photo : default.jpg
             */

            private int id;
            private String first_name;
            private String last_name;
            private String profile_photo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getProfile_photo() {
                return profile_photo;
            }

            public void setProfile_photo(String profile_photo) {
                this.profile_photo = profile_photo;
            }
        }
    }
}
