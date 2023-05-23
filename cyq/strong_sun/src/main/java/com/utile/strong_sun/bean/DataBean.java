package com.utile.strong_sun.bean;

import java.util.List;

public class DataBean {

    private String count;
    private Integer resultType;
    private List<PoisBean> pois;
    private List<PromptBean> prompt;
    private StatusBean status;
    private String keyWord;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public List<PoisBean> getPois() {
        return pois;
    }

    public void setPois(List<PoisBean> pois) {
        this.pois = pois;
    }

    public List<PromptBean> getPrompt() {
        return prompt;
    }

    public void setPrompt(List<PromptBean> prompt) {
        this.prompt = prompt;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public static class StatusBean {
        private String cndesc;
        private Integer infocode;

        public String getCndesc() {
            return cndesc;
        }

        public void setCndesc(String cndesc) {
            this.cndesc = cndesc;
        }

        public Integer getInfocode() {
            return infocode;
        }

        public void setInfocode(Integer infocode) {
            this.infocode = infocode;
        }
    }

    public static class PoisBean {
        private String address;
        private String phone;
        private String poiType;
        private String name;
        private String source;
        private String hotPointID;
        private String lonlat;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPoiType() {
            return poiType;
        }

        public void setPoiType(String poiType) {
            this.poiType = poiType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getHotPointID() {
            return hotPointID;
        }

        public void setHotPointID(String hotPointID) {
            this.hotPointID = hotPointID;
        }

        public String getLonlat() {
            return lonlat;
        }

        public void setLonlat(String lonlat) {
            this.lonlat = lonlat;
        }
    }

    public static class PromptBean {
        private Integer type;
        private List<AdminsBean> admins;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public List<AdminsBean> getAdmins() {
            return admins;
        }

        public void setAdmins(List<AdminsBean> admins) {
            this.admins = admins;
        }

        public static class AdminsBean {
            private String adminName;
            private Integer adminCode;

            public String getAdminName() {
                return adminName;
            }

            public void setAdminName(String adminName) {
                this.adminName = adminName;
            }

            public Integer getAdminCode() {
                return adminCode;
            }

            public void setAdminCode(Integer adminCode) {
                this.adminCode = adminCode;
            }
        }
    }
}
