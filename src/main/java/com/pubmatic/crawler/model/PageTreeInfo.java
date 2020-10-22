package com.pubmatic.crawler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * PageTreeInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-21-10T17:39:21.740Z")

public class PageTreeInfo implements Serializable {

    private static final long serialVersionUID = -8520553341221909293L;

    @JsonProperty("url")
    private String url;

    private Boolean valid;

    @JsonProperty("childUrl")
    private List<PageTreeInfo> childUrl;

    /**
     * get model with url with default invalid
     *
     * @param url
     *            page url
     */
    public PageTreeInfo(final String url) {
        this.url = url;
        valid = false;
    }

    public PageTreeInfo url(final String url) {
        this.url = url;
        return this;
    }

    /**
     * page Url
     *
     * @return url
     **/
    @ApiModelProperty(value = "page Url")

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public PageTreeInfo childUrls(final List<PageTreeInfo> childUrls) {
        this.childUrl = childUrls;
        return this;
    }

    public PageTreeInfo addChildUrlsItem(final PageTreeInfo childUrlsItem) {
        if (childUrl == null) {
            childUrl = new ArrayList<>();
        }
        if (childUrlsItem != null) {
            childUrl.add(childUrlsItem);
        }
        return this;
    }

    /**
     * Get childUrls
     *
     * @return childUrls
     **/
    @ApiModelProperty(value = "")

    @Valid

    public List<PageTreeInfo> getChildUrl() {
        return childUrl;
    }

    public void setChildUrl(final List<PageTreeInfo> childUrl) {
        this.childUrl = childUrl;
    }

    @Override
    public boolean equals(final java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PageTreeInfo pageTreeInfo = (PageTreeInfo) o;
        return Objects.equals(url, pageTreeInfo.url)
                && Objects.equals(valid, pageTreeInfo.valid)
                && Objects.equals(childUrl, pageTreeInfo.childUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url,  valid, childUrl);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class PageTreeInfo {\n");

        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    valid: ").append(toIndentedString(valid)).append("\n");
        sb.append("    childUrls: ").append(toIndentedString(childUrl)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(final java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
