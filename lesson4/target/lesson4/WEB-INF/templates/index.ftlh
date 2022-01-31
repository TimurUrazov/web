<#-- @ftlvariable name="posts" type="java.util.List" -->

<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/post.css">

<@c.page>
    <ul style="list-style-type: none">
        <#list posts?reverse as post>
            <li style="margin-bottom: 3rem">
                <@c.makePost post false/>
            </li>
        </#list>
    </ul>
</@c.page>