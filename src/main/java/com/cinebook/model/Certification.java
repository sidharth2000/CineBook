package com.cinebook.model;

public enum Certification {
    C_G,         
    C_PG,        
    C_12A,      
    C_12,        
    C_15A,       
    C_15,        
    C_16,        
    C_18;         
    
    
    @Override
    public String toString() {
        return this.name().replace("C_", "");
    }

}

