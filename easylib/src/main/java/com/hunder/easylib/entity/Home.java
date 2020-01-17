package com.hunder.easylib.entity;

import java.util.List;

/**
 * Created by hp on 2016/7/11.
 */
public class Home {
    public int totalPage;
    public String kefu;
    public List<Banner> banner;
    public List<Top> tops;
    public List<Course> boutiqueList;
    public List<Course> courseList;
    public TopSingle top;
    public String books;
    public String face;
    public String netschool;
    public String allday;

    /*"content": "一个人想要优秀必须接受挑战",
        "startDate": "1517453162837",
        "pic": "/image/iC7w6ptRLI.jpg"*/
    public static class TopSingle {
        public String content;
        public long startDate;
        public String pic;
        public String books;
        public String face;
        public String netschool;
        public String allday;
    }

    public static class Course {
        public static final String MYCUSTOMTYPE_HOME_RECOMMEND_COURSE = "推荐课程";
        public static final String MYCUSTOMTYPE_HOME_BOUTIQUE_COURSE = "精品课程";

        public String myCustomType;
        public String advisoryLink;
        public int applicantionNumber;
        public int courseHour;
        public int courseId;
        public String href;
        public String name;
        public double price;
        public List<Tag> tags;
        public List<Teacher> teachers;

        public static class Tag {
            public int id;
            public String name;
        }

        public static class Teacher {
            public String avatar;
            public String name;
            public String summary;

            public String content; //云课堂课程,同summary
        }
    }

    public static class Top {
        public String title;
        public String href;
    }

    public static class Banner {
        public String img;
        public String href;
    }
}
