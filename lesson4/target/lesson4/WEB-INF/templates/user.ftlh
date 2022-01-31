<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="long" -->

<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/user.css">

<#if handle??>
    <#assign u = c.findBy(users, "handle", handle)!/>
<#elseif user_id??>
    <#assign u = c.findBy(users, "id", user_id)!/>
</#if>

<@c.page>
    <#if u?has_content>
        <#assign current_user = u[1]/>
        <div class="user-page">
            <div class="user-info">
                <div class="user-features">
                    <div class ="user-caption">
                        <@c.userlink current_user/>
                    </div>
                    <ul>
                        <li>
                            id: ${current_user.id}
                        </li>
                        <li>
                            handle: ${current_user.handle}
                        </li>
                        <li>
                            name: ${current_user.name}
                        </li>
                        <li>
                            posts: <@c.countPosts current_user/>
                        </li>
                    </ul>
                </div>
                <div class="user-pic">
                    <img src="/img/user.jpg" alt="User picture">
                </div>
            </div>
            <div class="arrows">
                <div class="left-arrow">
                    <@c.enableArrow user=u[0] pic="⟵"/>
                </div>
                <div class="right-arrow">
                    <@c.enableArrow user=u[2] pic="⟶"/>
                </div>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
