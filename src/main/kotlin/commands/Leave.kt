package commands

import ATTRIBUTE_CN
import ATTRIBUTE_UN
import AbstractCommand
import ChatService
import domain.EmptyChatRequest
import domain.Error
import io.netty.channel.ChannelHandlerContext


class Leave(chatService: ChatService) : AbstractCommand<EmptyChatRequest>("leave", chatService) {

    override fun tryParse(input: String):Pair< EmptyChatRequest?,Error?>? {
        if (!input.startsWith("/$command")) return null
        return Pair(EmptyChatRequest,null)
    }

    override suspend fun process(ctx: ChannelHandlerContext, req: EmptyChatRequest): Boolean {
        if (!ctx.channel().hasAttr(ATTRIBUTE_UN)) return true

        ctx.channel().attr(ATTRIBUTE_CN).set(null)
        ctx.channel().writeAndFlush("exited channel!")

        return true
    }

}