package com.periodicalsubscription.controller.util;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocation;
import com.periodicalsubscription.constant.PagingConstant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class PagingUtil {
    @LogInvocation
    public Pageable getPageableFromRequest(Integer page, Integer pageSize, String sortProp) {
        page = getCorrectPage(page);
        pageSize = getCorrectPageSize(pageSize);
        Sort sort = Sort.by(Sort.Direction.ASC, sortProp);
        return PageRequest.of(page - 1, pageSize, sort);
    }

    @LogInvocation
    private Integer getCorrectPage(Integer rawPage) {
        return rawPage <= 0 ? PagingConstant.FIRST_PAGE : rawPage;
    }

    @LogInvocation
    private Integer getCorrectPageSize(Integer rawPageSize) {
        int pageSize = rawPageSize <= 0 ? PagingConstant.DEFAULT_PAGE_SIZE : rawPageSize;
        pageSize = Math.min(pageSize, PagingConstant.MAX_PAGE_SIZE);
        return pageSize;
    }

    @LogInvocation
    public <T> void setAttributesForPagingDisplay(Model model, Integer pageSize, Page<T> page, String command) {
        model.addAttribute("currentPage", page.getPageable().getPageNumber() + 1);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("command", command);
        model.addAttribute("pageSize", pageSize);
    }
}
