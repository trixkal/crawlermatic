
package com.pubmatic.crawler.model;

import java.io.Serializable;

import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1883879051549981029L;

    private String url;

    private Elements links;
}
