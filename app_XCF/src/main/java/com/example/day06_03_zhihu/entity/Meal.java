package com.example.day06_03_zhihu.entity;

import java.util.Arrays;

/**
 * Created by tarena on 2017/6/10.
 */

public class Meal {

    private String resultcode;
    private String reason;
    private Result result;

    @Override
    public String toString() {
        return "Meal{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
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

    public Meal() {

    }

    public Meal(String resultcode, String reason, Result result) {

        this.resultcode = resultcode;
        this.reason = reason;
        this.result = result;
    }

    public static class Result {
        private Data[] data;

        public Data[] getData() {
            return data;
        }

        public void setData(Data[] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "data=" + Arrays.toString(data) +
                    '}';
        }

        public Result() {
        }

        public Result(Data[] data) {

            this.data = data;
        }

        public static class Data{
            private String id;
            private String title;
            private String tags;
            private String imtro;
            private String ingredients;
            private String burden;
            private String[] albums;
            private Steps[] steps;

            @Override
            public String toString() {
                return "Data{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", tags='" + tags + '\'' +
                        ", imtro='" + imtro + '\'' +
                        ", ingredients='" + ingredients + '\'' +
                        ", burden='" + burden + '\'' +
                        ", albums=" + Arrays.toString(albums) +
                        ", steps=" + Arrays.toString(steps) +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getImtro() {
                return imtro;
            }

            public void setImtro(String imtro) {
                this.imtro = imtro;
            }

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public String getBurden() {
                return burden;
            }

            public void setBurden(String burden) {
                this.burden = burden;
            }

            public String[] getAlbums() {
                return albums;
            }

            public void setAlbums(String[] albums) {
                this.albums = albums;
            }

            public Steps[] getSteps() {
                return steps;
            }

            public void setSteps(Steps[] steps) {
                this.steps = steps;
            }

            public Data() {

            }

            public Data(String id, String title, String tags, String imtro, String ingredients, String burden, String[] albums, Steps[] steps) {

                this.id = id;
                this.title = title;
                this.tags = tags;
                this.imtro = imtro;
                this.ingredients = ingredients;
                this.burden = burden;
                this.albums = albums;
                this.steps = steps;
            }

            public static class Steps{
                private String img;
                private String step;

                @Override
                public String toString() {
                    return "Steps{" +
                            "img='" + img + '\'' +
                            ", step='" + step + '\'' +
                            '}';
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getStep() {
                    return step;
                }

                public void setStep(String step) {
                    this.step = step;
                }

                public Steps() {
                }

                public Steps(String img, String step) {

                    this.img = img;
                    this.step = step;
                }
            }


        }

    }

}
