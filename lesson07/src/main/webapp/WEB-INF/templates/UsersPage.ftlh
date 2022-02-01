<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
            <template>
                <th class="admin">Admin</th>
            </template>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user-id"></td>
                    <td class="user_login"></td>
                    <td class="admin">
                        <button class="enable-disable"></button>
                        <div class="noData"></div>
                    </td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");
            ajax({action: "findAll"},
            function (response) {
                if (response["isAdmin"] !== undefined && response["isAdmin"]) {
                    $(".users thead tr").append($($(".users thead template").prop("content")));
                }
                response["users"].forEach(function (user) {
                    $noData.hide();
                    const $tr = $($(".users tbody template").prop("content")).clone();
                    $tr.find(".user-id").text(user["id"]);
                    $tr.find(".user_login").text(user["login"]);
                    if (response["isAdmin"] !== undefined && response["isAdmin"]) {
                        $tr.find("tr").attr("data-adminId", response["user"].id);
                        $tr.find(".enable-disable").text(user["admin"] ? "Disable" : "Enable");
                    } else {
                        $tr.find(".enable-disable").closest("td").hide();
                    }
                    $tr.find("tr").attr("data-userId", user["id"]);
                    $tr.find("tr").attr("data-userLogin", user["login"]);

                    $(".users tbody").append($tr);
                });

                $(".users .enable-disable").click(function () {
                    const $tr = $(this).closest("tr")
                    const userId = $tr.attr("data-userId");
                    const userLogin = $tr.attr("data-userLogin");

                    const $button = $(this);
                    const $row = $(this).closest("tr");
                    const $noData = $row.find(".noData");
                    $noData.text("Loading...");

                    const admin = $(this).text() === "Enable";

                    ajax({action: "updateAdmin", userId, admin},
                        function () {
                            $noData.text("");
                            $button.text(admin ? "Disable" : "Enable");
                            if ($tr.attr("data-adminId") === userId) {
                                $(".users tbody .admin").remove();
                                $(".users thead .admin").remove();
                                notify("You are not admin anymore");
                            } else {
                                notify("User '" + userLogin + "' is " + (admin ? "admin" : "not admin anymore"));
                            }
                        })
                });
            });
        })
    </script>
</@c.page>
