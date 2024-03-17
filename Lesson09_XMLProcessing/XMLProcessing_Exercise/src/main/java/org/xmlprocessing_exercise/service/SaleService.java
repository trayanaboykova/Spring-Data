package org.xmlprocessing_exercise.service;

import jakarta.xml.bind.JAXBException;

public interface SaleService {
    void seedSales();
    void exportSales() throws JAXBException;
}
