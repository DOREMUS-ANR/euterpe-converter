/* CVS $Id: $ */
package org.doremus.euterpeConverter.ontology;
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;

/**
 * Vocabulary definitions from https://raw.githubusercontent.com/DOREMUS-ANR/doremus-ontology/master/FRBRoo-v2.4-Biblissima-2016-04-17.owl
 * @author Auto-generated by schemagen on 17 giu 2016 14:47
 */
public class FRBROO {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );

    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://erlangen-crm.org/efrbroo/";

    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}

    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );

    /** <p>The ontology's owl:versionInfo as a string</p> */
    public static final String VERSION_INFO = "This is a version of the FRBRoo 2.4 specifications in OWL that is based on the structure of the current efrbroo and the text file (odt), realised by Biblissima April 2O16";

    public static final ObjectProperty CLP104_subject_to = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLP104_subject_to" );

    public static final ObjectProperty CLP105_right_held_by = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLP105_right_held_by" );

    public static final ObjectProperty CLP2_should_have_type = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLP2_should_have_type" );

    public static final ObjectProperty CLP43_should_have_dimension = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLP43_should_have_dimension" );

    public static final ObjectProperty CLP45_should_consist_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLP45_should_consist_of" );

    public static final ObjectProperty CLP46_should_be_composed_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLP46_should_be_composed_of" );

    public static final ObjectProperty CLP57_should_have_number_of_parts = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLP57_should_have_number_of_parts" );

    public static final ObjectProperty CLR6_should_carry = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/CLR6_should_carry" );

    public static final ObjectProperty R10_has_member = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R10_has_member" );

    public static final ObjectProperty R11_has_issuing_rule = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R11_has_issuing_rule" );

    public static final ObjectProperty R12_is_realised_in = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R12_is_realised_in" );

    public static final ObjectProperty R13_is_realised_in = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R13_is_realised_in" );

    public static final ObjectProperty R15_has_fragment = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R15_has_fragment" );

    public static final ObjectProperty R16_initiated = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R16_initiated" );

    public static final ObjectProperty R17_created = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R17_created" );

    public static final ObjectProperty R18_created = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R18_created" );

    public static final ObjectProperty R19_created_a_realisation_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R19_created_a_realisation_of" );

    public static final ObjectProperty R1_is_logical_successor_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R1_is_logical_successor_of" );

    public static final ObjectProperty R20_recorded = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R20_recorded" );

    public static final ObjectProperty R21_created = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R21_created" );

    public static final ObjectProperty R22_created_a_realisation_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R22_created_a_realisation_of" );

    public static final ObjectProperty R23_created_a_realisation_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R23_created_a_realisation_of" );

    public static final ObjectProperty R24_created = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R24_created" );

    public static final ObjectProperty R25_performed = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R25_performed" );

    public static final ObjectProperty R26_produced_things_of_type = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R26_produced_things_of_type" );

    public static final ObjectProperty R27_used_as_source_material = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R27_used_as_source_material" );

    public static final ObjectProperty R28_produced = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R28_produced" );

    public static final ObjectProperty R29_reproduced = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R29_reproduced" );

    public static final ObjectProperty R2_is_derivative_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R2_is_derivative_of" );

    public static final ObjectProperty R30_produced = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R30_produced" );

    public static final ObjectProperty R31_is_reproduction_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R31_is_reproduction_of" );

    public static final ObjectProperty R32_is_warranted_by = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R32_is_warranted_by" );

    public static final ObjectProperty R33_has_content = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R33_has_content" );

    public static final ObjectProperty R34_has_validity_period = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R34_has_validity_period" );

    public static final ObjectProperty R35_is_specified_by = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R35_is_specified_by" );

    public static final ObjectProperty R36_uses_script_conversion = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R36_uses_script_conversion" );

    public static final ObjectProperty R37_states_as_nomen = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R37_states_as_nomen" );

    public static final ObjectProperty R38_refers_to_thema = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R38_refers_to_thema" );

    public static final ObjectProperty R39_is_intended_for = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R39_is_intended_for" );

    public static final ObjectProperty R3_is_realised_in = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R3_is_realised_in" );

    public static final ObjectProperty R40_has_representative_expression = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R40_has_representative_expression" );

    public static final ObjectProperty R41_has_representative_manifestation_product_type = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R41_has_representative_manifestation_product_type" );

    public static final ObjectProperty R42_is_representative_manifestation_singleton_for = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R42_is_representative_manifestation_singleton_for" );

    public static final ObjectProperty R43_carried_out_by = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R43_carried_out_by" );

    public static final ObjectProperty R44_carried_out_by = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R44_carried_out_by" );

    public static final ObjectProperty R45_assigned_to = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R45_assigned_to" );

    public static final ObjectProperty R46_assigned = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R46_assigned" );

    public static final ObjectProperty R48_assigned_to = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R48_assigned_to" );

    public static final ObjectProperty R49_assigned = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R49_assigned" );

    public static final ObjectProperty R4_carriers_provided_by = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R4_carriers_provided_by" );

    public static final ObjectProperty R50_assigned_to = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R50_assigned_to" );

    public static final ObjectProperty R51_assigned = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R51_assigned" );

    public static final ObjectProperty R52_used_rule = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R52_used_rule" );

    public static final ObjectProperty R53_assigned = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R53_assigned" );

    public static final ObjectProperty R54_has_nomen_language = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R54_has_nomen_language" );

    public static final ObjectProperty R55_has_nomen_form = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R55_has_nomen_form" );

    public static final ObjectProperty R56_has_related_use = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R56_has_related_use" );

    public static final ObjectProperty R57_is_based_on = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R57_is_based_on" );

    public static final ObjectProperty R58_has_fictional_member = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R58_has_fictional_member" );

    public static final ObjectProperty R59_had_typical_subject = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R59_had_typical_subject" );

    public static final ObjectProperty R5_has_component = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R5_has_component" );

    public static final ObjectProperty R60_used_to_use_language = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R60_used_to_use_language" );

    public static final ObjectProperty R61_occurred_in_kind_of_context = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R61_occurred_in_kind_of_context" );

    public static final ObjectProperty R62_was_used_for_membership_in = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R62_was_used_for_membership_in" );

    public static final ObjectProperty R63_named = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R63_named" );

    public static final ObjectProperty R64_used_name = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R64_used_name" );

    public static final ObjectProperty R65_recorded_aspects_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R65_recorded_aspects_of" );

    public static final ObjectProperty R66_included_performed_version_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R66_included_performed_version_of" );

    public static final ObjectProperty R6_carries = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R6_carries" );

    public static final ObjectProperty R7_is_example_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R7_is_example_of" );

    public static final ObjectProperty R8_consists_of = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R8_consists_of" );

    public static final ObjectProperty R9_is_realised_in = m_model.createObjectProperty( "http://erlangen-crm.org/efrbroo/R9_is_realised_in" );

    public static final OntClass F10_Person = m_model.createClass( "http://erlangen-crm.org/efrbroo/F10_Person" );

    public static final OntClass F11_Corporate_Body = m_model.createClass( "http://erlangen-crm.org/efrbroo/F11_Corporate_Body" );

    public static final OntClass F12_Nomen = m_model.createClass( "http://erlangen-crm.org/efrbroo/F12_Nomen" );

    public static final OntClass F13_Identifier = m_model.createClass( "http://erlangen-crm.org/efrbroo/F13_Identifier" );

    public static final OntClass F14_Individual_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F14_Individual_Work" );

    public static final OntClass F15_Complex_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F15_Complex_Work" );

    public static final OntClass F16_Container_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F16_Container_Work" );

    public static final OntClass F17_Aggregation_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F17_Aggregation_Work" );

    public static final OntClass F18_Serial_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F18_Serial_Work" );

    public static final OntClass F19_Publication_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F19_Publication_Work" );

    public static final OntClass F1_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F1_Work" );

    public static final OntClass F20_Performance_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F20_Performance_Work" );

    public static final OntClass F21_Recording_Work = m_model.createClass( "http://erlangen-crm.org/efrbroo/F21_Recording_Work" );

    public static final OntClass F22_Self_Contained_Expression = m_model.createClass( "http://erlangen-crm.org/efrbroo/F22_Self-Contained_Expression" );

    public static final OntClass F23_Expression_Fragment = m_model.createClass( "http://erlangen-crm.org/efrbroo/F23_Expression_Fragment" );

    public static final OntClass F24_Publication_Expression = m_model.createClass( "http://erlangen-crm.org/efrbroo/F24_Publication_Expression" );

    public static final OntClass F25_Performance_Plan = m_model.createClass( "http://erlangen-crm.org/efrbroo/F25_Performance_Plan" );

    public static final OntClass F26_Recording = m_model.createClass( "http://erlangen-crm.org/efrbroo/F26_Recording" );

    public static final OntClass F27_Work_Conception = m_model.createClass( "http://erlangen-crm.org/efrbroo/F27_Work_Conception" );

    public static final OntClass F28_Expression_Creation = m_model.createClass( "http://erlangen-crm.org/efrbroo/F28_Expression_Creation" );

    public static final OntClass F29_Recording_Event = m_model.createClass( "http://erlangen-crm.org/efrbroo/F29_Recording_Event" );

    public static final OntClass F2_Expression = m_model.createClass( "http://erlangen-crm.org/efrbroo/F2_Expression" );

    public static final OntClass F30_Publication_Event = m_model.createClass( "http://erlangen-crm.org/efrbroo/F30_Publication_Event" );

    public static final OntClass F31_Performance = m_model.createClass( "http://erlangen-crm.org/efrbroo/F31_Performance" );

    public static final OntClass F32_Carrier_Production_Event = m_model.createClass( "http://erlangen-crm.org/efrbroo/F32_Carrier_Production_Event" );

    public static final OntClass F33_Reproduction_Event = m_model.createClass( "http://erlangen-crm.org/efrbroo/F33_Reproduction_Event" );

    public static final OntClass F34_KOS = m_model.createClass( "http://erlangen-crm.org/efrbroo/F34_KOS" );

    public static final OntClass F35_Nomen_Use_Statement = m_model.createClass( "http://erlangen-crm.org/efrbroo/F35_Nomen_Use_Statement" );

    public static final OntClass F36_Script_Conversion = m_model.createClass( "http://erlangen-crm.org/efrbroo/F36_Script_Conversion" );

    public static final OntClass F38_Character = m_model.createClass( "http://erlangen-crm.org/efrbroo/F38_Character" );

    public static final OntClass F39_Family = m_model.createClass( "http://erlangen-crm.org/efrbroo/F39_Family" );

    public static final OntClass F3_Manifestation_Product_Type = m_model.createClass( "http://erlangen-crm.org/efrbroo/F3_Manifestation_Product_Type" );

    public static final OntClass F40_Identifier_Assignment = m_model.createClass( "http://erlangen-crm.org/efrbroo/F40_Identifier_Assignment" );

    public static final OntClass F41_Representative_Manifestation_Assignment = m_model.createClass( "http://erlangen-crm.org/efrbroo/F41_Representative_Manifestation_Assignment" );

    public static final OntClass F42_Representative_Expression_Assignment = m_model.createClass( "http://erlangen-crm.org/efrbroo/F42_Representative_Expression_Assignment" );

    public static final OntClass F43_Identifier_Rule = m_model.createClass( "http://erlangen-crm.org/efrbroo/F43_Identifier_Rule" );

    public static final OntClass F44_Bibliographic_Agency = m_model.createClass( "http://erlangen-crm.org/efrbroo/F44_Bibliographic_Agency" );

    public static final OntClass F4_Manifestation_Singleton = m_model.createClass( "http://erlangen-crm.org/efrbroo/F4_Manifestation_Singleton" );

    public static final OntClass F50_Controlled_Access_Point = m_model.createClass( "http://erlangen-crm.org/efrbroo/F50_Controlled_Access_Point" );

    public static final OntClass F51_Pursuit = m_model.createClass( "http://erlangen-crm.org/efrbroo/F51_Pursuit" );

    public static final OntClass F52_Name_Use_Activity = m_model.createClass( "http://erlangen-crm.org/efrbroo/F52_Name_Use_Activity" );

    public static final OntClass F53_Material_Copy = m_model.createClass( "http://erlangen-crm.org/efrbroo/F53_Material_Copy" );

    public static final OntClass F54_Utilised_Information_Carrier = m_model.createClass( "http://erlangen-crm.org/efrbroo/F54_Utilised_Information_Carrier" );

    public static final OntClass F5_Item = m_model.createClass( "http://erlangen-crm.org/efrbroo/F5_Item" );

    public static final OntClass F6_Concept = m_model.createClass( "http://erlangen-crm.org/efrbroo/F6_Concept" );

    public static final OntClass F7_Object = m_model.createClass( "http://erlangen-crm.org/efrbroo/F7_Object" );

    public static final OntClass F8_Event = m_model.createClass( "http://erlangen-crm.org/efrbroo/F8_Event" );

    public static final OntClass F9_Place = m_model.createClass( "http://erlangen-crm.org/efrbroo/F9_Place" );

}
