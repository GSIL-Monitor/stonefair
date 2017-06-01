<#if transaction.boothNumber?exists>
展位号：${transaction.boothNumber}
</#if>

<#if transaction.companyZh?exists>
公司名：${transaction.companyZh}
</#if>

<#if transaction.invoice_company?exists>
公司注册登记全名(全称)：${transaction.invoice_company}
</#if>

<#if transaction.invoice_taxpayer_no?exists>
公司纳税人识别号：${transaction.invoice_taxpayer_no}
</#if>

<#if transaction.invoice_bank_name?exists>
开户行：${transaction.invoice_bank_name}
</#if>

<#if transaction.invoice_bank_account?exists>
开户帐号：${transaction.invoice_bank_account}
</#if>

<#if transaction.invoice_company_address?exists>
公司地址：${transaction.invoice_company_address}
</#if>

<#if transaction.invoice_company_tel?exists>
公司电话：${transaction.invoice_company_tel}
</#if>

<#if transaction.invoice_company_contact?exists>
联系人：${transaction.invoice_company_contact}
</#if>

<#if transaction.invoice_general_taxpayer_flag?exists && transaction.invoice_general_taxpayer_flag=='1'>
是否为增值税一般纳税人：是
<#elseif transaction.invoice_general_taxpayer_flag?exists && transaction.invoice_general_taxpayer_flag=='2'>
是否为增值税一般纳税人：否
</#if>

<#if transaction.invoice_general_tax_type?exists && transaction.invoice_general_tax_type=='1'>
需要开具增值税发票类型：增值税普通发票
<#elseif transaction.invoice_general_tax_type?exists && transaction.invoice_general_tax_type=='2'>
是否为增值税一般纳税人：增值税专用发票
</#if>