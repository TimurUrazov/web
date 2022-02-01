<#-- @ftlvariable name="posts" type="java.util.List" -->
<#-- @ftlvariable name="post.user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->

<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/post.css">

<@c.page>
    <ul style="list-style-type: none">
        <#list posts?reverse as post>
            <#if user_id?? && user_id == post.user_id>
                <li style="margin-bottom: 3rem">
                    <@c.makePost post false/>
                </li>
            </#if>
        </#list>
    </ul>
</@c.page>
