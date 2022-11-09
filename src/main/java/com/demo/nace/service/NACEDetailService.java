package com.demo.nace.service;

import com.demo.nace.exception.CSVParseException;
import com.demo.nace.model.entity.NACEDetail;
import com.demo.nace.repository.NACEDetailRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class NACEDetailService{

    @Autowired
    NACEDetailRepository naceDetailRepository;

    public Map<String, Object> parseAndPersistCSV(InputStream inputStream) throws CSVParseException {
        Reader in = new InputStreamReader(inputStream);
        try {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            ArrayList<NACEDetail> naceDetails = new ArrayList<>();

            for (CSVRecord record : records) {
                NACEDetail naceDetail = new NACEDetail();
                naceDetail.setNaceOrder(record.get(0));
                naceDetail.setLevel(record.get(1));
                naceDetail.setCode(record.get(2));
                naceDetail.setParent(record.get(3));
                naceDetail.setDescription(record.get(4));
                naceDetail.setItemIncludes(record.get(5));
                naceDetail.setItemAlsoIncludes(record.get(6));
                naceDetail.setRulings(record.get(7));
                naceDetail.setItemExcludes(record.get(8));
                naceDetail.setReferenceToISICRev4(record.get(9));

                naceDetails.add(naceDetail);
            }
            System.out.println(naceDetails.size());

            naceDetailRepository.saveAll(naceDetails);

            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", "success");
            successResponse.put("message", "Successfully uploaded the CSV");
            return successResponse;
        }catch (Exception exception){
            throw new CSVParseException(exception.getMessage());
        }
    }

    public Optional<NACEDetail> findNACEDetailById(String orderId){
        return naceDetailRepository.findById(orderId);
    }

}
