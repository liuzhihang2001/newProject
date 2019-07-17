package com.yonyou.iuap.annex.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnexExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AnnexExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdBillIsNull() {
            addCriterion("ID_BILL is null");
            return (Criteria) this;
        }

        public Criteria andIdBillIsNotNull() {
            addCriterion("ID_BILL is not null");
            return (Criteria) this;
        }

        public Criteria andIdBillEqualTo(String value) {
            addCriterion("ID_BILL =", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillNotEqualTo(String value) {
            addCriterion("ID_BILL <>", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillGreaterThan(String value) {
            addCriterion("ID_BILL >", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillGreaterThanOrEqualTo(String value) {
            addCriterion("ID_BILL >=", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillLessThan(String value) {
            addCriterion("ID_BILL <", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillLessThanOrEqualTo(String value) {
            addCriterion("ID_BILL <=", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillLike(String value) {
            addCriterion("ID_BILL like", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillNotLike(String value) {
            addCriterion("ID_BILL not like", value, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillIn(List<String> values) {
            addCriterion("ID_BILL in", values, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillNotIn(List<String> values) {
            addCriterion("ID_BILL not in", values, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillBetween(String value1, String value2) {
            addCriterion("ID_BILL between", value1, value2, "idBill");
            return (Criteria) this;
        }

        public Criteria andIdBillNotBetween(String value1, String value2) {
            addCriterion("ID_BILL not between", value1, value2, "idBill");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("FILENAME is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("FILENAME is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("FILENAME =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("FILENAME <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("FILENAME >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("FILENAME >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("FILENAME <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("FILENAME <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("FILENAME like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("FILENAME not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("FILENAME in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("FILENAME not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("FILENAME between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("FILENAME not between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("CREATOR is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("CREATOR is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("CREATOR =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("CREATOR <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("CREATOR >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("CREATOR >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("CREATOR <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("CREATOR <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("CREATOR like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("CREATOR not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("CREATOR in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("CREATOR not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("CREATOR between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("CREATOR not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCommittimeIsNull() {
            addCriterion("COMMITTIME is null");
            return (Criteria) this;
        }

        public Criteria andCommittimeIsNotNull() {
            addCriterion("COMMITTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCommittimeEqualTo(Date value) {
            addCriterion("COMMITTIME =", value, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeNotEqualTo(Date value) {
            addCriterion("COMMITTIME <>", value, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeGreaterThan(Date value) {
            addCriterion("COMMITTIME >", value, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeGreaterThanOrEqualTo(Date value) {
            addCriterion("COMMITTIME >=", value, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeLessThan(Date value) {
            addCriterion("COMMITTIME <", value, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeLessThanOrEqualTo(Date value) {
            addCriterion("COMMITTIME <=", value, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeIn(List<Date> values) {
            addCriterion("COMMITTIME in", values, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeNotIn(List<Date> values) {
            addCriterion("COMMITTIME not in", values, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeBetween(Date value1, Date value2) {
            addCriterion("COMMITTIME between", value1, value2, "committime");
            return (Criteria) this;
        }

        public Criteria andCommittimeNotBetween(Date value1, Date value2) {
            addCriterion("COMMITTIME not between", value1, value2, "committime");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDownloadurlIsNull() {
            addCriterion("DOWNLOADURL is null");
            return (Criteria) this;
        }

        public Criteria andDownloadurlIsNotNull() {
            addCriterion("DOWNLOADURL is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadurlEqualTo(String value) {
            addCriterion("DOWNLOADURL =", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlNotEqualTo(String value) {
            addCriterion("DOWNLOADURL <>", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlGreaterThan(String value) {
            addCriterion("DOWNLOADURL >", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlGreaterThanOrEqualTo(String value) {
            addCriterion("DOWNLOADURL >=", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlLessThan(String value) {
            addCriterion("DOWNLOADURL <", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlLessThanOrEqualTo(String value) {
            addCriterion("DOWNLOADURL <=", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlLike(String value) {
            addCriterion("DOWNLOADURL like", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlNotLike(String value) {
            addCriterion("DOWNLOADURL not like", value, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlIn(List<String> values) {
            addCriterion("DOWNLOADURL in", values, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlNotIn(List<String> values) {
            addCriterion("DOWNLOADURL not in", values, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlBetween(String value1, String value2) {
            addCriterion("DOWNLOADURL between", value1, value2, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andDownloadurlNotBetween(String value1, String value2) {
            addCriterion("DOWNLOADURL not between", value1, value2, "downloadurl");
            return (Criteria) this;
        }

        public Criteria andN1IsNull() {
            addCriterion("N1 is null");
            return (Criteria) this;
        }

        public Criteria andN1IsNotNull() {
            addCriterion("N1 is not null");
            return (Criteria) this;
        }

        public Criteria andN1EqualTo(String value) {
            addCriterion("N1 =", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1NotEqualTo(String value) {
            addCriterion("N1 <>", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1GreaterThan(String value) {
            addCriterion("N1 >", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1GreaterThanOrEqualTo(String value) {
            addCriterion("N1 >=", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1LessThan(String value) {
            addCriterion("N1 <", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1LessThanOrEqualTo(String value) {
            addCriterion("N1 <=", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1Like(String value) {
            addCriterion("N1 like", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1NotLike(String value) {
            addCriterion("N1 not like", value, "n1");
            return (Criteria) this;
        }

        public Criteria andN1In(List<String> values) {
            addCriterion("N1 in", values, "n1");
            return (Criteria) this;
        }

        public Criteria andN1NotIn(List<String> values) {
            addCriterion("N1 not in", values, "n1");
            return (Criteria) this;
        }

        public Criteria andN1Between(String value1, String value2) {
            addCriterion("N1 between", value1, value2, "n1");
            return (Criteria) this;
        }

        public Criteria andN1NotBetween(String value1, String value2) {
            addCriterion("N1 not between", value1, value2, "n1");
            return (Criteria) this;
        }

        public Criteria andN2IsNull() {
            addCriterion("N2 is null");
            return (Criteria) this;
        }

        public Criteria andN2IsNotNull() {
            addCriterion("N2 is not null");
            return (Criteria) this;
        }

        public Criteria andN2EqualTo(String value) {
            addCriterion("N2 =", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2NotEqualTo(String value) {
            addCriterion("N2 <>", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2GreaterThan(String value) {
            addCriterion("N2 >", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2GreaterThanOrEqualTo(String value) {
            addCriterion("N2 >=", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2LessThan(String value) {
            addCriterion("N2 <", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2LessThanOrEqualTo(String value) {
            addCriterion("N2 <=", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2Like(String value) {
            addCriterion("N2 like", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2NotLike(String value) {
            addCriterion("N2 not like", value, "n2");
            return (Criteria) this;
        }

        public Criteria andN2In(List<String> values) {
            addCriterion("N2 in", values, "n2");
            return (Criteria) this;
        }

        public Criteria andN2NotIn(List<String> values) {
            addCriterion("N2 not in", values, "n2");
            return (Criteria) this;
        }

        public Criteria andN2Between(String value1, String value2) {
            addCriterion("N2 between", value1, value2, "n2");
            return (Criteria) this;
        }

        public Criteria andN2NotBetween(String value1, String value2) {
            addCriterion("N2 not between", value1, value2, "n2");
            return (Criteria) this;
        }

        public Criteria andN3IsNull() {
            addCriterion("N3 is null");
            return (Criteria) this;
        }

        public Criteria andN3IsNotNull() {
            addCriterion("N3 is not null");
            return (Criteria) this;
        }

        public Criteria andN3EqualTo(String value) {
            addCriterion("N3 =", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3NotEqualTo(String value) {
            addCriterion("N3 <>", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3GreaterThan(String value) {
            addCriterion("N3 >", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3GreaterThanOrEqualTo(String value) {
            addCriterion("N3 >=", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3LessThan(String value) {
            addCriterion("N3 <", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3LessThanOrEqualTo(String value) {
            addCriterion("N3 <=", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3Like(String value) {
            addCriterion("N3 like", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3NotLike(String value) {
            addCriterion("N3 not like", value, "n3");
            return (Criteria) this;
        }

        public Criteria andN3In(List<String> values) {
            addCriterion("N3 in", values, "n3");
            return (Criteria) this;
        }

        public Criteria andN3NotIn(List<String> values) {
            addCriterion("N3 not in", values, "n3");
            return (Criteria) this;
        }

        public Criteria andN3Between(String value1, String value2) {
            addCriterion("N3 between", value1, value2, "n3");
            return (Criteria) this;
        }

        public Criteria andN3NotBetween(String value1, String value2) {
            addCriterion("N3 not between", value1, value2, "n3");
            return (Criteria) this;
        }

        public Criteria andN4IsNull() {
            addCriterion("N4 is null");
            return (Criteria) this;
        }

        public Criteria andN4IsNotNull() {
            addCriterion("N4 is not null");
            return (Criteria) this;
        }

        public Criteria andN4EqualTo(String value) {
            addCriterion("N4 =", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4NotEqualTo(String value) {
            addCriterion("N4 <>", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4GreaterThan(String value) {
            addCriterion("N4 >", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4GreaterThanOrEqualTo(String value) {
            addCriterion("N4 >=", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4LessThan(String value) {
            addCriterion("N4 <", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4LessThanOrEqualTo(String value) {
            addCriterion("N4 <=", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4Like(String value) {
            addCriterion("N4 like", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4NotLike(String value) {
            addCriterion("N4 not like", value, "n4");
            return (Criteria) this;
        }

        public Criteria andN4In(List<String> values) {
            addCriterion("N4 in", values, "n4");
            return (Criteria) this;
        }

        public Criteria andN4NotIn(List<String> values) {
            addCriterion("N4 not in", values, "n4");
            return (Criteria) this;
        }

        public Criteria andN4Between(String value1, String value2) {
            addCriterion("N4 between", value1, value2, "n4");
            return (Criteria) this;
        }

        public Criteria andN4NotBetween(String value1, String value2) {
            addCriterion("N4 not between", value1, value2, "n4");
            return (Criteria) this;
        }

        public Criteria andN5IsNull() {
            addCriterion("N5 is null");
            return (Criteria) this;
        }

        public Criteria andN5IsNotNull() {
            addCriterion("N5 is not null");
            return (Criteria) this;
        }

        public Criteria andN5EqualTo(BigDecimal value) {
            addCriterion("N5 =", value, "n5");
            return (Criteria) this;
        }

        public Criteria andN5NotEqualTo(BigDecimal value) {
            addCriterion("N5 <>", value, "n5");
            return (Criteria) this;
        }

        public Criteria andN5GreaterThan(BigDecimal value) {
            addCriterion("N5 >", value, "n5");
            return (Criteria) this;
        }

        public Criteria andN5GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("N5 >=", value, "n5");
            return (Criteria) this;
        }

        public Criteria andN5LessThan(BigDecimal value) {
            addCriterion("N5 <", value, "n5");
            return (Criteria) this;
        }

        public Criteria andN5LessThanOrEqualTo(BigDecimal value) {
            addCriterion("N5 <=", value, "n5");
            return (Criteria) this;
        }

        public Criteria andN5In(List<BigDecimal> values) {
            addCriterion("N5 in", values, "n5");
            return (Criteria) this;
        }

        public Criteria andN5NotIn(List<BigDecimal> values) {
            addCriterion("N5 not in", values, "n5");
            return (Criteria) this;
        }

        public Criteria andN5Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("N5 between", value1, value2, "n5");
            return (Criteria) this;
        }

        public Criteria andN5NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("N5 not between", value1, value2, "n5");
            return (Criteria) this;
        }

        public Criteria andN6IsNull() {
            addCriterion("N6 is null");
            return (Criteria) this;
        }

        public Criteria andN6IsNotNull() {
            addCriterion("N6 is not null");
            return (Criteria) this;
        }

        public Criteria andN6EqualTo(BigDecimal value) {
            addCriterion("N6 =", value, "n6");
            return (Criteria) this;
        }

        public Criteria andN6NotEqualTo(BigDecimal value) {
            addCriterion("N6 <>", value, "n6");
            return (Criteria) this;
        }

        public Criteria andN6GreaterThan(BigDecimal value) {
            addCriterion("N6 >", value, "n6");
            return (Criteria) this;
        }

        public Criteria andN6GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("N6 >=", value, "n6");
            return (Criteria) this;
        }

        public Criteria andN6LessThan(BigDecimal value) {
            addCriterion("N6 <", value, "n6");
            return (Criteria) this;
        }

        public Criteria andN6LessThanOrEqualTo(BigDecimal value) {
            addCriterion("N6 <=", value, "n6");
            return (Criteria) this;
        }

        public Criteria andN6In(List<BigDecimal> values) {
            addCriterion("N6 in", values, "n6");
            return (Criteria) this;
        }

        public Criteria andN6NotIn(List<BigDecimal> values) {
            addCriterion("N6 not in", values, "n6");
            return (Criteria) this;
        }

        public Criteria andN6Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("N6 between", value1, value2, "n6");
            return (Criteria) this;
        }

        public Criteria andN6NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("N6 not between", value1, value2, "n6");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}