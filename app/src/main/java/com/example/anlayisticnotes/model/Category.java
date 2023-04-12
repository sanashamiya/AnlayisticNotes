package com.example.anlayisticnotes.model;

public class Category {


     String id;
     String Titel;

     private  Category(){}

    public Category(String id, String Titel) {
            this.id = id;
            this.Titel = Titel;

        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getTitelCategory() {
            return Titel;
        }


}
