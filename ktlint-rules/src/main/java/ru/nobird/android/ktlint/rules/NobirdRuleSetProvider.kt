package ru.nobird.android.ktlint.rules

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider

class NobirdRuleSetProvider : RuleSetProvider {
    override fun get(): RuleSet =
        RuleSet(
            "nobird-ktlint-rules",
            ExpressionBodyRule(),
            ExpressionBodyIndentRule(),
            ExplicitPublicTypeRule(),
            IfElseRule()
        )
}