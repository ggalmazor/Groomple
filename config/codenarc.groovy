ruleset {
	description 'Gradle plugins CodeNarc RuleSet'

	ruleset('http://codenarc.sourceforge.net/StarterRuleSet-AllRulesByCategory.groovy.txt') {
		BracesForIfElse(enabled: false)
		IfStatementBraces(enabled: false)
		MethodName(regex: '[a-z][a-zA-Z_0-9 ]*')
		UnnecessaryReturnKeyword(enabled: false)
	}
}