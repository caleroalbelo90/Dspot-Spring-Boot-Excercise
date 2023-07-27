package com.dspot.profile.main.model.profile;

import java.util.List;

public class ProfilePage {

    List<Profile> content;
    ProfilePageable pageable;

    int totalPages;
    int totalElements;
    boolean last;
    int size;
    int number;
    SortPageable sort;

    int numberOfElements;

    boolean first;

    boolean empty;

    public List<Profile> getContent() {
        return content;
    }

    public void setContent(List<Profile> content) {
        this.content = content;
    }

    public ProfilePageable getPageable() {
        return pageable;
    }

    public void setPageable(ProfilePageable pageable) {
        this.pageable = pageable;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public SortPageable getSort() {
        return sort;
    }

    public void setSort(SortPageable sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}




