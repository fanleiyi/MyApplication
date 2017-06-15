package employee.karen.tarena.com.imusicmanager.entity;


import java.util.Arrays;

/**
 * Created by tarena on 2017/6/8.
 */

public class weather {

    private String reason;
    private Result result;
    private int error_code;
    public static class Result{
        @Override
        public String toString() {
            return "Result{" +
                    "data=" + data +
                    '}';
        }

        private Data data;
        public static class Data{
            @Override
            public String toString() {
                return "Data{" +
                        "realtime=" + realtime +
                        ", life=" + life +
                        ", weather=" + Arrays.toString(weather) +
                        ", pm25=" + pm25 +
                        ", jingqu='" + jingqu + '\'' +
                        ", date='" + date + '\'' +
                        ", isForeign='" + isForeign + '\'' +
                        '}';
            }

            private Realtime realtime;
            private Life life;
            private Weather[] weather;
            private Pm25 pm25;
            private String jingqu;
            private String date;
            private String isForeign;

            public static class Weather{

                private String date;
                private Info info;
                private String week;
                private String nongli;
                public static class Info{
                    @Override
                    public String toString() {
                        return "Info{" +
                                "day=" + Arrays.toString(day) +
                                ", dawn=" + Arrays.toString(dawn) +
                                ", night=" + Arrays.toString(night) +
                                '}';
                    }

                    private String[] day;
                    private String[] dawn;
                    private String[] night;

                    public Info(String[] day, String[] dawn, String[] night) {
                        this.day = day;
                        this.dawn = dawn;
                        this.night = night;
                    }

                    public String[] getDay() {
                        return day;
                    }

                    public void setDay(String[] day) {
                        this.day = day;
                    }

                    public String[] getDawn() {
                        return dawn;
                    }

                    public void setDawn(String[] dawn) {
                        this.dawn = dawn;
                    }

                    public String[] getNight() {
                        return night;
                    }

                    public void setNight(String[] night) {
                        this.night = night;
                    }
                }

                public Weather() {
                }

                @Override
                public String toString() {
                    return "Weather{" +
                            "date='" + date + '\'' +
                            ", info=" + info +
                            ", week='" + week + '\'' +
                            ", nongli='" + nongli + '\'' +
                            '}';
                }

                public Weather(String date, Info info, String week, String nongli) {
                    this.date = date;
                    this.info = info;
                    this.week = week;
                    this.nongli = nongli;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public Info getInfo() {
                    return info;
                }

                public void setInfo(Info info) {
                    this.info = info;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getNongli() {
                    return nongli;
                }

                public void setNongli(String nongli) {
                    this.nongli = nongli;
                }
            }

            public static class Realtime{
                private String city_code;
                private String city_name;
                private String date;
                private String time;
                private int week;
                private String moon;
                private long dataUptime;
                private Weather weather;
                private Wind wind;

                @Override
                public String toString() {
                    return "Realtime{" +
                            "city_code='" + city_code + '\'' +
                            ", city_name='" + city_name + '\'' +
                            ", date='" + date + '\'' +
                            ", time='" + time + '\'' +
                            ", week=" + week +
                            ", moon='" + moon + '\'' +
                            ", dataUptime=" + dataUptime +
                            ", weather=" + weather +
                            ", wind=" + wind +
                            '}';
                }

                public static class Wind{
                    private String direct;
                    private String power;
                    private String offset;
                    private String windspeed;

                    @Override
                    public String toString() {
                        return "Wind{" +
                                "direct='" + direct + '\'' +
                                ", power='" + power + '\'' +
                                ", offset='" + offset + '\'' +
                                ", windspeed='" + windspeed + '\'' +
                                '}';
                    }

                    public Wind() {
                    }

                    public Wind(String direct, String power, String offset, String windspeed) {
                        this.direct = direct;
                        this.power = power;
                        this.offset = offset;
                        this.windspeed = windspeed;
                    }

                    public String getDirect() {
                        return direct;
                    }

                    public void setDirect(String direct) {
                        this.direct = direct;
                    }

                    public String getPower() {
                        return power;
                    }

                    public void setPower(String power) {
                        this.power = power;
                    }

                    public String getOffset() {
                        return offset;
                    }

                    public void setOffset(String offset) {
                        this.offset = offset;
                    }

                    public String getWindspeed() {
                        return windspeed;
                    }

                    public void setWindspeed(String windspeed) {
                        this.windspeed = windspeed;
                    }
                }
                public static class Weather{
                    private String temperature;
                    private String humidity;
                    private String info;
                    private String img;

                    @Override
                    public String toString() {
                        return "Weather{" +
                                "temperature='" + temperature + '\'' +
                                ", humidity='" + humidity + '\'' +
                                ", info='" + info + '\'' +
                                ", img='" + img + '\'' +
                                '}';
                    }

                    public Weather() {
                    }

                    public Weather(String temperature, String humidity, String info, String img) {
                        this.temperature = temperature;
                        this.humidity = humidity;
                        this.info = info;
                        this.img = img;
                    }

                    public String getTemperature() {
                        return temperature;
                    }

                    public void setTemperature(String temperature) {
                        this.temperature = temperature;
                    }

                    public String getHumidity() {
                        return humidity;
                    }

                    public void setHumidity(String humidity) {
                        this.humidity = humidity;
                    }

                    public String getInfo() {
                        return info;
                    }

                    public void setInfo(String info) {
                        this.info = info;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }
                }

                public Realtime() {
                }

                public Realtime(String city_code, String city_name, String date, String time, int week, String moon, long dataUptime, Weather weather, Wind wind) {
                    this.city_code = city_code;
                    this.city_name = city_name;
                    this.date = date;
                    this.time = time;
                    this.week = week;
                    this.moon = moon;
                    this.dataUptime = dataUptime;
                    this.weather = weather;
                    this.wind = wind;
                }

                public String getCity_code() {
                    return city_code;
                }

                public void setCity_code(String city_code) {
                    this.city_code = city_code;
                }

                public String getCity_name() {
                    return city_name;
                }

                public void setCity_name(String city_name) {
                    this.city_name = city_name;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public int getWeek() {
                    return week;
                }

                public void setWeek(int week) {
                    this.week = week;
                }

                public String getMoon() {
                    return moon;
                }

                public void setMoon(String moon) {
                    this.moon = moon;
                }

                public long getDataUptime() {
                    return dataUptime;
                }

                public void setDataUptime(long dataUptime) {
                    this.dataUptime = dataUptime;
                }

                public Weather getWeather() {
                    return weather;
                }

                public void setWeather(Weather weather) {
                    this.weather = weather;
                }

                public Wind getWind() {
                    return wind;
                }

                public void setWind(Wind wind) {
                    this.wind = wind;
                }

            }
            public static class Life{
                private String date;
                private Info info;

                @Override
                public String toString() {
                    return "Life{" +
                            "date='" + date + '\'' +
                            ", info=" + info +
                            '}';
                }

                public static class Info{
                    @Override
                    public String toString() {
                        return "Info{" +
                                "chuanyi=" + Arrays.toString(chuanyi) +
                                ", ganmao=" + Arrays.toString(ganmao) +
                                ", kongtiao=" + Arrays.toString(kongtiao) +
                                ", wuran=" + Arrays.toString(wuran) +
                                ", xiche=" + Arrays.toString(xiche) +
                                ", yundong=" + Arrays.toString(yundong) +
                                ", ziwaixian=" + Arrays.toString(ziwaixian) +
                                '}';
                    }

                    private String[] chuanyi;
                    private String[] ganmao;
                    private String[] kongtiao;
                    private String[] wuran;
                    private String[] xiche;
                    private String[] yundong;
                    private String[] ziwaixian;

                    public Info() {
                    }

                    public Info(String[] chuanyi, String[] ganmao, String[] kongtiao, String[] wuran, String[] xiche, String[] yundong, String[] ziwaixian) {
                        this.chuanyi = chuanyi;
                        this.ganmao = ganmao;
                        this.kongtiao = kongtiao;
                        this.wuran = wuran;
                        this.xiche = xiche;
                        this.yundong = yundong;
                        this.ziwaixian = ziwaixian;
                    }

                    public String[] getChuanyi() {
                        return chuanyi;
                    }

                    public void setChuanyi(String[] chuanyi) {
                        this.chuanyi = chuanyi;
                    }

                    public String[] getGanmao() {
                        return ganmao;
                    }

                    public void setGanmao(String[] ganmao) {
                        this.ganmao = ganmao;
                    }

                    public String[] getKongtiao() {
                        return kongtiao;
                    }

                    public void setKongtiao(String[] kongtiao) {
                        this.kongtiao = kongtiao;
                    }

                    public String[] getWuran() {
                        return wuran;
                    }

                    public void setWuran(String[] wuran) {
                        this.wuran = wuran;
                    }

                    public String[] getXiche() {
                        return xiche;
                    }

                    public void setXiche(String[] xiche) {
                        this.xiche = xiche;
                    }

                    public String[] getYundong() {
                        return yundong;
                    }

                    public void setYundong(String[] yundong) {
                        this.yundong = yundong;
                    }

                    public String[] getZiwaixian() {
                        return ziwaixian;
                    }

                    public void setZiwaixian(String[] ziwaixian) {
                        this.ziwaixian = ziwaixian;
                    }
                }

                public Life() {
                }

                public Life(String date, Info info) {
                    this.date = date;
                    this.info = info;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public Info getInfo() {
                    return info;
                }

                public void setInfo(Info info) {
                    this.info = info;
                }
            }
            public static class Pm25{
                @Override
                public String toString() {
                    return "Pm25{" +
                            "key='" + key + '\'' +
                            ", show_desc=" + show_desc +
                            ", pm25=" + pm25 +
                            ", dateTime='" + dateTime + '\'' +
                            ", cityName='" + cityName + '\'' +
                            '}';
                }

                private String key;
                private int show_desc;
                private  Pm_25 pm25;
                private String dateTime;
                private String cityName;
                public static class Pm_25{
                    @Override
                    public String toString() {
                        return "Pm_25{" +
                                "curPm='" + curPm + '\'' +
                                ", pm25='" + pm25 + '\'' +
                                ", pm10='" + pm10 + '\'' +
                                ", level='" + level + '\'' +
                                ", quality='" + quality + '\'' +
                                ", des='" + des + '\'' +
                                '}';
                    }

                    private String curPm;
                    private String pm25;
                    private String pm10;
                    private String level;
                    private String quality;
                    private String des;

                    public Pm_25() {
                    }

                    public Pm_25(String curPm, String pm25, String pm10, String level, String quality, String des) {
                        this.curPm = curPm;
                        this.pm25 = pm25;
                        this.pm10 = pm10;
                        this.level = level;
                        this.quality = quality;
                        this.des = des;
                    }

                    public String getCurPm() {
                        return curPm;
                    }

                    public void setCurPm(String curPm) {
                        this.curPm = curPm;
                    }

                    public String getPm25() {
                        return pm25;
                    }

                    public void setPm25(String pm25) {
                        this.pm25 = pm25;
                    }

                    public String getPm10() {
                        return pm10;
                    }

                    public void setPm10(String pm10) {
                        this.pm10 = pm10;
                    }

                    public String getLevel() {
                        return level;
                    }

                    public void setLevel(String level) {
                        this.level = level;
                    }

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public String getDes() {
                        return des;
                    }

                    public void setDes(String des) {
                        this.des = des;
                    }
                }

                public Pm25() {
                }

                public Pm25(String key, int show_desc, Pm_25 pm25, String dateTime, String cityName) {
                    this.key = key;
                    this.show_desc = show_desc;
                    this.pm25 = pm25;
                    this.dateTime = dateTime;
                    this.cityName = cityName;
                }

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public int getShow_desc() {
                    return show_desc;
                }

                public void setShow_desc(int show_desc) {
                    this.show_desc = show_desc;
                }

                public Pm_25 getPm25() {
                    return pm25;
                }

                public void setPm25(Pm_25 pm25) {
                    this.pm25 = pm25;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }
            }

            public Data() {
            }

            public Data(Realtime realtime, Life life, Weather[] weather, Pm25 pm25, String jingqu, String date, String isForeign) {
                this.realtime = realtime;
                this.life = life;
                this.weather = weather;
                this.pm25 = pm25;
                this.jingqu = jingqu;
                this.date = date;
                this.isForeign = isForeign;
            }

            public Realtime getRealtime() {
                return realtime;
            }

            public void setRealtime(Realtime realtime) {
                this.realtime = realtime;
            }

            public Life getLife() {
                return life;
            }

            public void setLife(Life life) {
                this.life = life;
            }

            public Weather[] getWeather() {
                return weather;
            }

            public void setWeather(Weather[] weather) {
                this.weather = weather;
            }

            public Pm25 getPm25() {
                return pm25;
            }

            public void setPm25(Pm25 pm25) {
                this.pm25 = pm25;
            }

            public String getJingqu() {
                return jingqu;
            }

            public void setJingqu(String jingqu) {
                this.jingqu = jingqu;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getIsForeign() {
                return isForeign;
            }

            public void setIsForeign(String isForeign) {
                this.isForeign = isForeign;
            }
        }

        public Result() {
        }

        public Result(Data data) {

            this.data = data;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public weather() {
    }

    public weather(String reason, Result result, int error_code) {
        this.reason = reason;
        this.result = result;
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "weather{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
