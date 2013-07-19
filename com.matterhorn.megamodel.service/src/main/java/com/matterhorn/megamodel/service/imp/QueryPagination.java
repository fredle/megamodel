package com.matterhorn.megamodel.service.imp;

import javax.persistence.Query;

import com.matterhorn.megamodel.domain.transport.Pagination;

public final class QueryPagination {

	private QueryPagination() {}

	public static Query paginate(Query query, Pagination pager)
	{
		query.setMaxResults(pager.size);
		query.setFirstResult(pager.start);
		return query;
	}
}
