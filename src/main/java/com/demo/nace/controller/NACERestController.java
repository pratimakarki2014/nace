package com.demo.nace.controller;

//import com.univocity.parsers.common.Context;
//import com.univocity.parsers.common.DataProcessingException;
//import com.univocity.parsers.common.ProcessorErrorHandler;
//import com.univocity.parsers.common.processor.ColumnProcessor;
//import com.univocity.parsers.common.processor.core.Processor;
//import com.univocity.parsers.common.record.Record;
//import com.univocity.parsers.csv.CsvFormat;
//import com.univocity.parsers.csv.CsvParser;
//import com.univocity.parsers.csv.CsvParserSettings;

import com.demo.nace.exception.CSVParseException;
import com.demo.nace.exception.NACEDetailNotFoundException;
import com.demo.nace.model.entity.NACEDetail;
import com.demo.nace.repository.NACEDetailRepository;
import com.demo.nace.service.NACEDetailService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/nace-detail")
@Api(value = "", tags = {"NACE Detail API"})
@Tag(name = "NACE Detail API", description = "NACE Detail")
public class NACERestController {

    @Autowired
    NACEDetailService naceDetailService;

    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> storeNACEDetail(@RequestPart("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new CSVParseException("File is empty");
        }
        InputStream inputStream = file.getInputStream();
        try {
            var response = naceDetailService.parseAndPersistCSV(inputStream);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            throw new CSVParseException(exception.getMessage());
        }
    }

    @GetMapping(value = "/{naceOrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NACEDetail> getNACEByOrderId(@PathVariable("naceOrderId") String naceOrderId) throws Exception {
        Optional<NACEDetail> customer = naceDetailService.findNACEDetailById(naceOrderId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            throw new NACEDetailNotFoundException("NACE Detail not found");
        }
    }
}
