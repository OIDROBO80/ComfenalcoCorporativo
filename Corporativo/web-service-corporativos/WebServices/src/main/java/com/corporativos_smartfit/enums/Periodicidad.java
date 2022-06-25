package com.corporativos_smartfit.enums;

public enum Periodicidad {
    MENSUAL(System.getProperty("daysToValidateAgreementsMonthly"),30),
    TRIMESTRE(System.getProperty("daysToValidateAgreementsTrimester"),90),
    ANUAL(System.getProperty("daysToValidateAgreementsAnnual"),365);

    private String daysToValidateAgreements;
    private int daysToPeriod;

    Periodicidad(String daysToValidateAgreements, int daysToPeriod){
        this.daysToValidateAgreements=daysToValidateAgreements;
        this.daysToPeriod=daysToPeriod;
    }

    public int getDaysToValidateAgreements(){
        return Integer.parseInt(this.daysToValidateAgreements);
    }
    public int getDaysToPeriod(){
        return this.daysToPeriod;
    }

}
