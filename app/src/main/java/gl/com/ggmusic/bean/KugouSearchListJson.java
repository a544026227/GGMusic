package gl.com.ggmusic.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by guilinlin on 16/4/19 16:34.
 * desc:搜索酷狗详细信息对应的bean
 */
public class KugouSearchListJson {



    private String error;


    private DataBean data;
    private int status;
    private int errcode;
    /**
     * isblock : 1
     * type : 0
     */

    private BlackBean black;
    /**
     * priortype : 1
     * singer : null
     */

    private RelativeBean relative;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public BlackBean getBlack() {
        return black;
    }

    public void setBlack(BlackBean black) {
        this.black = black;
    }

    public RelativeBean getRelative() {
        return relative;
    }

    public void setRelative(RelativeBean relative) {
        this.relative = relative;
    }

    public static class DataBean {
        private int timestamp;
        private int correctiontype;
        private int total;
        private int istag;
        private String correctiontip;
        private int forcecorrection;
        private int istagresult;

        private List<InfoBean> info;

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public int getCorrectiontype() {
            return correctiontype;
        }

        public void setCorrectiontype(int correctiontype) {
            this.correctiontype = correctiontype;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getIstag() {
            return istag;
        }

        public void setIstag(int istag) {
            this.istag = istag;
        }

        public String getCorrectiontip() {
            return correctiontip;
        }

        public void setCorrectiontip(String correctiontip) {
            this.correctiontip = correctiontip;
        }

        public int getForcecorrection() {
            return forcecorrection;
        }

        public void setForcecorrection(int forcecorrection) {
            this.forcecorrection = forcecorrection;
        }

        public int getIstagresult() {
            return istagresult;
        }

        public void setIstagresult(int istagresult) {
            this.istagresult = istagresult;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            private String filename;
            private String songname;
            private int m4afilesize;
            @SerializedName("320hash")
            private String value320hash;
            private String mvhash;
            private int privilege;
            private int filesize;
            private String source;
            private int bitrate;
            private int ownercount;
            private String album_name;
            private String topic;
            @SerializedName("320filesize")
            private int value320filesize;
            private int isnew;
            private int duration;
            private String album_id;
            private int Accompany;
            private String singername;
            private String extname;
            @SerializedName("320privilege")
            private int value320privilege;
            private int sourceid;
            private int srctype;
            private int feetype;
            private int sqfilesize;
            private String hash;
            private int sqprivilege;
            private String sqhash;
            private String othername;
            /**
             * filename : 陈奕迅 - K歌之王
             * songname : K歌之王
             * m4afilesize : 930486
             * 320hash : 8c3503116b50dae8d7bf758e11a340d1
             * mvhash : c92c3df94becc6a7724ef731f72ed205
             * privilege : 8
             * filesize : 3590144
             * source :
             * bitrate : 128
             * ownercount : 137620
             * album_name : sound & sight
             * topic :
             * 320filesize : 9056306
             * isnew : 0
             * duration : 224
             * album_id : 504180
             * Accompany : 1
             * singername : 陈奕迅
             * extname : mp3
             * 320privilege : 10
             * sourceid : 0
             * srctype : 1
             * feetype : 0
             * sqfilesize : 22681467
             * hash : 78e146748d81289bda4185fc97508d5b
             * sqprivilege : 10
             * sqhash : 88345678a0e3992a86da48e956e26040
             * othername :
             */

            private List<GroupBean> group;

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getSongname() {
                return songname;
            }

            public void setSongname(String songname) {
                this.songname = songname;
            }

            public int getM4afilesize() {
                return m4afilesize;
            }

            public void setM4afilesize(int m4afilesize) {
                this.m4afilesize = m4afilesize;
            }

            public String getValue320hash() {
                return value320hash;
            }

            public void setValue320hash(String value320hash) {
                this.value320hash = value320hash;
            }

            public String getMvhash() {
                return mvhash;
            }

            public void setMvhash(String mvhash) {
                this.mvhash = mvhash;
            }

            public int getPrivilege() {
                return privilege;
            }

            public void setPrivilege(int privilege) {
                this.privilege = privilege;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public int getBitrate() {
                return bitrate;
            }

            public void setBitrate(int bitrate) {
                this.bitrate = bitrate;
            }

            public int getOwnercount() {
                return ownercount;
            }

            public void setOwnercount(int ownercount) {
                this.ownercount = ownercount;
            }

            public String getAlbum_name() {
                return album_name;
            }

            public void setAlbum_name(String album_name) {
                this.album_name = album_name;
            }

            public String getTopic() {
                return topic;
            }

            public void setTopic(String topic) {
                this.topic = topic;
            }

            public int getValue320filesize() {
                return value320filesize;
            }

            public void setValue320filesize(int value320filesize) {
                this.value320filesize = value320filesize;
            }

            public int getIsnew() {
                return isnew;
            }

            public void setIsnew(int isnew) {
                this.isnew = isnew;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getAlbum_id() {
                return album_id;
            }

            public void setAlbum_id(String album_id) {
                this.album_id = album_id;
            }

            public int getAccompany() {
                return Accompany;
            }

            public void setAccompany(int Accompany) {
                this.Accompany = Accompany;
            }

            public String getSingername() {
                return singername;
            }

            public void setSingername(String singername) {
                this.singername = singername;
            }

            public String getExtname() {
                return extname;
            }

            public void setExtname(String extname) {
                this.extname = extname;
            }

            public int getValue320privilege() {
                return value320privilege;
            }

            public void setValue320privilege(int value320privilege) {
                this.value320privilege = value320privilege;
            }

            public int getSourceid() {
                return sourceid;
            }

            public void setSourceid(int sourceid) {
                this.sourceid = sourceid;
            }

            public int getSrctype() {
                return srctype;
            }

            public void setSrctype(int srctype) {
                this.srctype = srctype;
            }

            public int getFeetype() {
                return feetype;
            }

            public void setFeetype(int feetype) {
                this.feetype = feetype;
            }

            public int getSqfilesize() {
                return sqfilesize;
            }

            public void setSqfilesize(int sqfilesize) {
                this.sqfilesize = sqfilesize;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            public int getSqprivilege() {
                return sqprivilege;
            }

            public void setSqprivilege(int sqprivilege) {
                this.sqprivilege = sqprivilege;
            }

            public String getSqhash() {
                return sqhash;
            }

            public void setSqhash(String sqhash) {
                this.sqhash = sqhash;
            }

            public String getOthername() {
                return othername;
            }

            public void setOthername(String othername) {
                this.othername = othername;
            }

            public List<GroupBean> getGroup() {
                return group;
            }

            public void setGroup(List<GroupBean> group) {
                this.group = group;
            }

            public static class GroupBean {
                private String filename;
                private String songname;
                private int m4afilesize;
                @SerializedName("320hash")
                private String value320hash;
                private String mvhash;
                private int privilege;
                private int filesize;
                private String source;
                private int bitrate;
                private int ownercount;
                private String album_name;
                private String topic;
                @SerializedName("320filesize")
                private int value320filesize;
                private int isnew;
                private int duration;
                private String album_id;
                private int Accompany;
                private String singername;
                private String extname;
                @SerializedName("320privilege")
                private int value320privilege;
                private int sourceid;
                private int srctype;
                private int feetype;
                private int sqfilesize;
                private String hash;
                private int sqprivilege;
                private String sqhash;
                private String othername;

                public String getFilename() {
                    return filename;
                }

                public void setFilename(String filename) {
                    this.filename = filename;
                }

                public String getSongname() {
                    return songname;
                }

                public void setSongname(String songname) {
                    this.songname = songname;
                }

                public int getM4afilesize() {
                    return m4afilesize;
                }

                public void setM4afilesize(int m4afilesize) {
                    this.m4afilesize = m4afilesize;
                }

                public String getValue320hash() {
                    return value320hash;
                }

                public void setValue320hash(String value320hash) {
                    this.value320hash = value320hash;
                }

                public String getMvhash() {
                    return mvhash;
                }

                public void setMvhash(String mvhash) {
                    this.mvhash = mvhash;
                }

                public int getPrivilege() {
                    return privilege;
                }

                public void setPrivilege(int privilege) {
                    this.privilege = privilege;
                }

                public int getFilesize() {
                    return filesize;
                }

                public void setFilesize(int filesize) {
                    this.filesize = filesize;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public int getBitrate() {
                    return bitrate;
                }

                public void setBitrate(int bitrate) {
                    this.bitrate = bitrate;
                }

                public int getOwnercount() {
                    return ownercount;
                }

                public void setOwnercount(int ownercount) {
                    this.ownercount = ownercount;
                }

                public String getAlbum_name() {
                    return album_name;
                }

                public void setAlbum_name(String album_name) {
                    this.album_name = album_name;
                }

                public String getTopic() {
                    return topic;
                }

                public void setTopic(String topic) {
                    this.topic = topic;
                }

                public int getValue320filesize() {
                    return value320filesize;
                }

                public void setValue320filesize(int value320filesize) {
                    this.value320filesize = value320filesize;
                }

                public int getIsnew() {
                    return isnew;
                }

                public void setIsnew(int isnew) {
                    this.isnew = isnew;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public String getAlbum_id() {
                    return album_id;
                }

                public void setAlbum_id(String album_id) {
                    this.album_id = album_id;
                }

                public int getAccompany() {
                    return Accompany;
                }

                public void setAccompany(int Accompany) {
                    this.Accompany = Accompany;
                }

                public String getSingername() {
                    return singername;
                }

                public void setSingername(String singername) {
                    this.singername = singername;
                }

                public String getExtname() {
                    return extname;
                }

                public void setExtname(String extname) {
                    this.extname = extname;
                }

                public int getValue320privilege() {
                    return value320privilege;
                }

                public void setValue320privilege(int value320privilege) {
                    this.value320privilege = value320privilege;
                }

                public int getSourceid() {
                    return sourceid;
                }

                public void setSourceid(int sourceid) {
                    this.sourceid = sourceid;
                }

                public int getSrctype() {
                    return srctype;
                }

                public void setSrctype(int srctype) {
                    this.srctype = srctype;
                }

                public int getFeetype() {
                    return feetype;
                }

                public void setFeetype(int feetype) {
                    this.feetype = feetype;
                }

                public int getSqfilesize() {
                    return sqfilesize;
                }

                public void setSqfilesize(int sqfilesize) {
                    this.sqfilesize = sqfilesize;
                }

                public String getHash() {
                    return hash;
                }

                public void setHash(String hash) {
                    this.hash = hash;
                }

                public int getSqprivilege() {
                    return sqprivilege;
                }

                public void setSqprivilege(int sqprivilege) {
                    this.sqprivilege = sqprivilege;
                }

                public String getSqhash() {
                    return sqhash;
                }

                public void setSqhash(String sqhash) {
                    this.sqhash = sqhash;
                }

                public String getOthername() {
                    return othername;
                }

                public void setOthername(String othername) {
                    this.othername = othername;
                }
            }
        }
    }

    public static class BlackBean {
        private int isblock;
        private int type;

        public int getIsblock() {
            return isblock;
        }

        public void setIsblock(int isblock) {
            this.isblock = isblock;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class RelativeBean {
        private int priortype;
        private Object singer;

        public int getPriortype() {
            return priortype;
        }

        public void setPriortype(int priortype) {
            this.priortype = priortype;
        }

        public Object getSinger() {
            return singer;
        }

        public void setSinger(Object singer) {
            this.singer = singer;
        }
    }
}
