package ru.nobird.android.ktlint.rules

import com.pinterest.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.visibilityModifierTypeOrDefault

class ExplicitPublicTypeRule : Rule("explicit-public-type") {
    override fun visit(node: ASTNode, autoCorrect: Boolean, emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        when (val element = node.psi ?: return) {
            is KtNamedFunction -> {
                if (element.visibilityModifierTypeOrDefault() == KtTokens.PUBLIC_KEYWORD &&
                    element.equalsToken != null &&
                    !element.hasDeclaredReturnType()) {
                    emit(node.startOffset, "Public methods should have explicitly declared return type", false)
                }
            }
        }
    }
}