package com.example.day06_03_zhihu.entity;

import java.util.Arrays;

/**
 * Created by tarena on 2017/6/10.
 */

public class Menu {

    private String resultcode;
    private String reason;
    private Result[] result;

    @Override
    public String toString() {
        return "Menu{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + Arrays.toString(result) +
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

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }

    public Menu() {

    }

    public Menu(String resultcode, String reason, Result[] result) {

        this.resultcode = resultcode;
        this.reason = reason;
        this.result = result;
    }

    public static class Result{
        private String parentId;
        private String name;
        private List list;

        @Override
        public String toString() {
            return "Result{" +
                    "parentId='" + parentId + '\'' +
                    ", name='" + name + '\'' +
                    ", list=" + list +
                    '}';
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }

        public Result() {

        }

        public Result(String parentId, String name, List list) {

            this.parentId = parentId;
            this.name = name;
            this.list = list;
        }

        public static class List{
            private String id;
            private String name;
            private String parentId;

            @Override
            public String toString() {
                return "List{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", parentId='" + parentId + '\'' +
                        '}';
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

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public List() {

            }

            public List(String id, String name, String parentId) {

                this.id = id;
                this.name = name;
                this.parentId = parentId;
            }
        }
    }

}
