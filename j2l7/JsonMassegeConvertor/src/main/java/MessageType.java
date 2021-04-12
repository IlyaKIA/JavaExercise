/**
 * Project java_core_l2
 *
 * @Author Alexander Grigorev
 * Created 09.03.2021
 * v1.0
 *
 * Перечисление типов сообщений. Чтоб потом удобно их категоризировать и обрабатывать.
 * Думаю, будем его расширять.
 */
public enum MessageType {
    PUBLIC_MESSAGE,
    PRIVATE_MESSAGE,
    CLIENTS_LIST_MESSAGE,
    SEND_AUTH_MESSAGE,
    AUTH_CONFIRM,
    ERROR_MESSAGE,
    CHANGE_NICK
}
