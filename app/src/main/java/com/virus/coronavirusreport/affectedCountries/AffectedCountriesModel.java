package com.virus.coronavirusreport.affectedCountries;

public class AffectedCountriesModel {
    private String flag;
    private String countryName;

    public AffectedCountriesModel() {
    }

    public AffectedCountriesModel(String flag, String countryName) {
        this.flag = flag;
        this.countryName = countryName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
