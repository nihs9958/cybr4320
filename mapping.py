class KMedians:
    def k_medians_operator(self):
        return (self.k_medians_keyword() +
                LPAREN + self.identifier() + COMMA + self.integer() + RPAREN).setParseAction(self.k_medians_action)

    def k_medians_action(self, tokens):
        field = tokens[2]
        k = int(tokens[4])
        return KMediansExp(field, k, self.memory)

class KMediansExp(OperatorExp):
    def __init__(self, field, k, memory):
        super().__init__(field, memory)
        self.k = k

    def create_op_string(self):
        lstream = self.memory.get(Constants.CURRENT_LSTREAM)
        rstream = self.memory.get(Constants.CURRENT_RSTREAM)

        # Get the tuple type of the input stream
        tuple_type = self.memory.get(lstream + Constants.TUPLE_TYPE)

        # Generate SAM code for the K-medians operator
        op_string = (f'identifier = "{lstream}";\n'
                     f'auto {lstream} = std::make_shared<KMedians<{tuple_type}>>({self.k});\n'
                     f'{self.add_register_statements(lstream, rstream, self.memory)}')

        return op_string
