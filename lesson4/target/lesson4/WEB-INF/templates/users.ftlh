<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/users.css">

<@c.page>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Login</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td><#if user?? && user.id == u.id>*</#if><@c.userlink u/></td>
                    <td>${u.handle}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>

