<#import "/spring.ftl" as spring>
<#import "./commons.ftlh" as c>

<@c.page>
    <#if user?has_content>
        <div class="user-page">
            <div class="user-info">
                <div class="user-features">
                    <div class ="user-caption">
                        User's profile
                    </div>
                    <ul>
                        <li>
                            id: ${user.id}
                        </li>
                        <li>
                            login: ${user.login}
                        </li>
                        <li>
                            registered: ${user.creationTime}
                        </li>
                    </ul>
                </div>
                <div class="user-pic">
                    <img src="<@spring.url '/img/user.jpg'/>" alt="User picture">
                </div>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
