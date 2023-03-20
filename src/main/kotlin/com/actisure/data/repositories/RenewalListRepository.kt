package com.actisure.data.repositories

import com.actisure.data.controllers.details.RenewalList
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository


interface RenewalListRepository : CrudRepository<RenewalList, String> {
   /* @Query("Select   policyNumber," +
            "     principalName," +
            "     renewalDate," +
            "     plan," +
            "     agentName,"+
            "     policyAmount,"+
            "     claims,"+
            "     email,"+
            "     mobile "+
            "from RenewalList")*/
    @Query("WITH policydetails\n" +
            "        AS (SELECT p.policyid,\n" +
            "                   e.entityid,\n" +
            "                   p.effectivedate,\n" +
            "                   pn.policyattributevalue policynumber,\n" +
            "                   firstname || ' ' || surname AS principalname,\n" +
            "                   CASE\n" +
            "                      WHEN len (a.policyattributevalue) = 19\n" +
            "                      THEN\n" +
            "                         TO_DATE (SUBSTR (a.policyattributevalue, 0, 10),\n" +
            "                                  'YYYY-MM-dd')\n" +
            "                      ELSE\n" +
            "                         TO_DATE (a.policyattributevalue)\n" +
            "                   END\n" +
            "                      AS renewaldate,\n" +
            "                   paymentmethod AS plan,\n" +
            "                   policyamount\n" +
            "              FROM policy p,\n" +
            "                   policyattribute pn,\n" +
            "                   policyattribute a,\n" +
            "                   entity e\n" +
            "             WHERE     p.policyid = a.policyid\n" +
            "                   AND p.effectivedate = a.effectivedate\n" +
            "                   AND pn.policyid = p.policyid\n" +
            "                   AND pn.policyattributeid = 18\n" +
            "                   AND pn.effectivedate = p.effectivedate\n" +
            "                   AND a.POLICYATTRIBUTEID = 21\n" +
            "                   AND p.policyholderid = e.entityid\n" +
            "                   AND policystatus = 'L'\n" +
            "                   AND productid = 49\n" +
            "                   AND policytype = 2),\n" +
            "        claimsdetails\n" +
            "        AS (  SELECT policyid,\n" +
            "                     a.effectivedate,\n" +
            "                     SUM (settledamount) settledamount\n" +
            "                FROM claimassessment a,\n" +
            "                     claimtreatment t,\n" +
            "                     claimtreatmentinvoice i,\n" +
            "                     claimtreatmentinvoiceline l\n" +
            "               WHERE     a.assessmentid = t.assessmentid\n" +
            "                     AND t.treatmentid = i.treatmentid\n" +
            "                     AND i.invoiceid = l.invoiceid\n" +
            "                     AND i.invoicestatus = 'Paid'\n" +
            "            GROUP BY policyid, a.effectivedate),\n" +
            "        interestedparties\n" +
            "        AS (SELECT i.policyid,\n" +
            "                   i.effectivedate,\n" +
            "                   entityid agentid,\n" +
            "                   NVL (companyname, (firstname || ' ' || surname))\n" +
            "                      AS agentname\n" +
            "              FROM policyextrelationinfo i, entity e\n" +
            "             WHERE     POLICYINFO IN\n" +
            "                          ('Tied Agent', 'Independent Agent', 'Broker')\n" +
            "                   AND POLICYINFOID = 27\n" +
            "                   AND E.ENTITYID =\n" +
            "                          REGEXP_SUBSTR (ExtRelationshipType, '[0-9]+')),\n" +
            "        phonenumber\n" +
            "        AS (SELECT entityid, phonenumber mobile\n" +
            "              FROM phone p, entityphone ph\n" +
            "             WHERE p.phoneid = ph.phoneid AND phonetype = 3),\n" +
            "        emails\n" +
            "        AS (SELECT entityid, info AS email\n" +
            "              FROM entityroleinfo\n" +
            "             WHERE infoid = 16)\n" +
            "   SELECT policynumber,\n" +
            "          principalname,\n" +
            "          renewaldate,\n" +
            "          plan,\n" +
            "          NVL (agentname, 'Direct') AS agentname,\n" +
            "          NVL (agentid, 0) asagentid,\n" +
            "          policyamount,\n" +
            "          NVL (settledamount, 0) AS claims,\n" +
            "          email,\n" +
            "          mobile\n" +
            "     FROM policydetails p,\n" +
            "          claimsdetails d,\n" +
            "          interestedparties i,\n" +
            "          phonenumber pn,\n" +
            "          emails em\n" +
            "    WHERE     p.policyid = d.policyid(+)\n" +
            "          AND p.effectivedate = d.effectivedate(+)\n" +
            "          AND p.policyid = i.policyid(+)\n" +
            "          AND p.effectivedate = i.effectivedate(+)\n" +
            "          AND p.entityid = pn.entityid(+)\n" +
            "          AND p.entityid = em.entityid(+)\n" +
            "          AND renewaldate BETWEEN (ADD_MONTHS (SYSDATE, -1))\n" +
            "                              AND ADD_MONTHS (SYSDATE, 1)\n" +
            "          AND policyamount IS NOT NULL")
    fun retrieveRenewalList():List<RenewalList>
}
