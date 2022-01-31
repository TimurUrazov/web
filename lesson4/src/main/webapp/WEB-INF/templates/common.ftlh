<#-- @ftlvariable name="current_page" type="java.lang.String" -->
<#-- @ftlvariable name="posts" type="java.util.List" -->
<#-- @ftlvariable name="links" type="java.util.List" -->
<#-- @ftlvariable name="link.href" type="java.lang.String" -->
<#-- @ftlvariable name="link.name" type="java.lang.String" -->

<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user=user nameOnly=true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <#list links as link>
                    <li>
                        <a href="${link.href}"
                                <#if current_page == link.href>
                                    style="text-decoration: none; border-bottom: 3px solid mediumblue"
                                </#if>
                        >${link.name}</a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <section>
                <div class="header">Post #${post.id}</div>
                <div class="body">
                    <@processText text=post.text showAll=false/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly=false>
<#-- @ftlvariable name="user" type="ru.itmo.web.lesson4.model.User" -->
    <a href="/user?handle=${user.handle}"
       <#if !nameOnly>
           style="text-decoration: none; color:
           <#switch user.color>
                <#case "RED">
                    red
                <#break>
               <#case "GREEN">
                   green
               <#break>
               <#case "BLUE">
                   blue
               <#break>
            </#switch>
            "
       </#if>
    >${user.name}</a>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return [items[item_index - 1]!, item, items[item_index + 1]!]/>
        </#if>
    </#list>
</#function>

<#macro enableArrow user pic>
    <#if user?has_content>
        <a href="/user?handle=${user.handle}" style="color: blue; text-decoration: none">${pic}</a>
    <#else>
        ${pic}
    </#if>
</#macro>

<#macro processText text showAll>
    <#assign counter = 255>
    <#list text?split("\n") as line>
        <li style="list-style-type: none">
            <#if !showAll>
                <#if line?length <= counter>
                    ${line}
                    <#assign counter -= line?length>
                <#else>
                    ${line?substring(0, counter)}...
                    <#break>
                </#if>
            <#else>
                ${line}
            </#if>
        </li>
    </#list>
</#macro>

<#macro makePost post showAll>
    <article>
        <div class="title">
            <a href="/post?post_id=${post.id}" style="text-decoration: none; color: var(--caption-color)">${post.title}</a>
        </div>
        <div class="information">By <@userlink user=findBy(users, "id", post.user_id)[1]/>, 2 days ago, translation</div>
        <div class="body">
            <@processText text=post.text showAll=showAll/>
        </div>
        <ul class="attachment">
            <li>Announcement of <a href="#">${post.title}</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#macro countPosts user>
    <#assign counter = 0>
    <#list posts as post>
        <#if user.id == post.user_id>
            <#assign counter++>
        </#if>
    </#list>
<#--   posts?filter()?size -->
    <#if counter == 0>
        0
    <#else>
        <a href="/posts?user_id=${user.id}">${counter}</a>
    </#if>
</#macro>