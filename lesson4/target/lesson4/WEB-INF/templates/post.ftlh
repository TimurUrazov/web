<#-- @ftlvariable name="posts" type="java.util.List" -->
<#-- @ftlvariable name="post_id" type="java.lang.String" -->

<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/post.css">

<#if post_id??>
    <#assign p = c.findBy(posts, "id", post_id)!/>
</#if>

<@c.page>
    <#if p?has_content>
        <@c.makePost post=p[1] showAll=true/>
    </#if>
</@c.page>

