package com.pdf;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;

public class Demo {

    public static void main(String[] args) throws Exception{
        Pdf pdf = new Pdf();

        pdf.addPageFromString("<html><head><meta charset=\"utf-8\"></head><h1>Müller</h1></html>");
        pdf.addPageFromUrl("http://www.google.com");

// Add a Table of Contents
        pdf.addToc();

// The `wkhtmltopdf` shell command accepts different types of options such as global, page, headers and footers, and toc. Please see `wkhtmltopdf -H` for a full explanation.
// All options are passed as array, for example:
        pdf.addParam(new Param("--no-footer-line"), new Param("--header-html", "file:///header.html"));
        pdf.addParam(new Param("--enable-javascript"));

// Add styling for Table of Contents
        pdf.addTocParam(new Param("--xsl-style-sheet", "my_toc.xsl"));

// Save the PDF
        pdf.saveAs("output.pdf");
    }
}
