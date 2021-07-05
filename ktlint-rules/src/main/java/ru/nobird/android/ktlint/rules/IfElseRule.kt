package ru.nobird.android.ktlint.rules

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType
import com.pinterest.ktlint.core.ast.children
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import org.jetbrains.kotlin.psi.KtIfExpression
import org.jetbrains.kotlin.psi.KtVariableDeclaration

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 09-Feb-21
 * https://github.com/bitvale
 */
class IfElseRule : Rule("single-multiline-if-else") {
    companion object {
        private const val MAX_EXPRESSION_SIZE = 100
        private const val INDENT_SIZE = 4
    }

    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        when (val element = node.psi ?: return) {
            is KtVariableDeclaration -> {
                val ifExpression = element.lastChild as? KtIfExpression ?: return

                if (ifExpression.text.length <= MAX_EXPRESSION_SIZE &&
                    !ifExpression.prevSibling.text.contains('\n') &&
                    !ifExpression.text.contains('{')
                ) return

                val incorrectOneLine = ifExpression.text.length > 100 &&
                        (!ifExpression.prevSibling.text.contains('\n') ||
                                !ifExpression.text.contains('{'))

                val incorrectMultiline = ifExpression.text.contains('{') &&
                        !ifExpression.prevSibling.text.contains('\n')

                if (incorrectOneLine || incorrectMultiline) {
                    emit(
                        node.startOffset,
                        "Is expression should be formatted correctly",
                        true
                    )

                    if (autoCorrect) {
                        val lastChildNode = node.lastChildNode

                        val afterEqualSpaces = node.children()
                            .first { it.elementType == ElementType.EQ }
                            .treeNext

                        node.replaceChild(afterEqualSpaces, PsiWhiteSpaceImpl(" "))
                        node.removeChild(lastChildNode)

                        node.addChild(LeafPsiElement(ElementType.DANGLING_NEWLINE, "\n"), null)
                        val indent =
                            if (element.prevSibling.text.contains("\n")) {
                                element.prevSibling.text.split("\n").last().length
                            } else {
                                element.prevSibling.textLength
                            }
                        node.addChild(PsiWhiteSpaceImpl(" ".repeat(indent + INDENT_SIZE)), null)

                        node.addChild(LeafPsiElement(ElementType.IF_KEYWORD, "if"), null)
                        node.addChild(PsiWhiteSpaceImpl(" "), null)
                        node.addChild(LeafPsiElement(ElementType.LPAR, "("), null)
                        node.addChild(
                            lastChildNode.children()
                                .first { it.elementType == ElementType.CONDITION },
                            null
                        )
                        node.addChild(LeafPsiElement(ElementType.RPAR, ")"), null)
                        node.addChild(PsiWhiteSpaceImpl(" "), null)
                        node.addChild(LeafPsiElement(ElementType.LBRACE, "{"), null)
                        node.addChild(LeafPsiElement(ElementType.DANGLING_NEWLINE, "\n"), null)
                        val firstBlock = lastChildNode.children()
                            .first { it.elementType == ElementType.THEN }
                            .text
                            .replace("{", "")
                            .replace("}", "")
                        node.addChild(PsiWhiteSpaceImpl(" ".repeat(indent + INDENT_SIZE * 2)), null)
                        node.addChild(LeafPsiElement(ElementType.BLOCK, firstBlock.trim()), null)
                        node.addChild(LeafPsiElement(ElementType.DANGLING_NEWLINE, "\n"), null)
                        node.addChild(PsiWhiteSpaceImpl(" ".repeat(indent + INDENT_SIZE)), null)
                        node.addChild(LeafPsiElement(ElementType.RBRACE, "}"), null)
                        node.addChild(PsiWhiteSpaceImpl(" "), null)
                        node.addChild(LeafPsiElement(ElementType.ELSE_KEYWORD, "else"), null)
                        node.addChild(PsiWhiteSpaceImpl(" "), null)
                        node.addChild(LeafPsiElement(ElementType.LBRACE, "{"), null)
                        node.addChild(LeafPsiElement(ElementType.DANGLING_NEWLINE, "\n"), null)
                        val secondBlock = lastChildNode.children()
                            .first { it.elementType == ElementType.ELSE }
                            .text
                            .replace("{", "")
                            .replace("}", "")
                        node.addChild(PsiWhiteSpaceImpl(" ".repeat(indent + INDENT_SIZE * 2)), null)
                        node.addChild(LeafPsiElement(ElementType.BLOCK, secondBlock.trim()), null)
                        node.addChild(LeafPsiElement(ElementType.DANGLING_NEWLINE, "\n"), null)
                        node.addChild(PsiWhiteSpaceImpl(" ".repeat(indent + INDENT_SIZE)), null)
                        node.addChild(LeafPsiElement(ElementType.RBRACE, "}"), null)
                    }
                }
            }
        }
    }
}