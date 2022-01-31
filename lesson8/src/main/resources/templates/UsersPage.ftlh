<#-- @ftlvariable name="user.id" type="Long" -->
<#-- @ftlvariable name="u.id" type="Long" -->

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if user?? && !user.disabled>
                    <th>Enable/Disable</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as u>
                <tr>
                    <td class="id">${u.id}</td>
                    <td class="login"><a href="<@spring.url '/user/${u.id?c}'/>">${u.login}</a></td>
                    <td class="creationTime">${u.creationTime}</td>
                    <#if user?? && !user.disabled>
                        <td class="enable-disable">
                            <form method="post" action="">
                                <input type="hidden" name="sourceId" value="${user.id?c}">
                                <input type="hidden" name="id" value="${u.id?c}">
                                <input type="hidden" name="disabled" value="${u.disabled?then("false" "true")}">
                                <div class="button-field">
                                    <input type="submit" value="${u.disabled?then("Enable" "Disable")}">
                                </div>
                            </form>
                        </td>
                    </#if>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
